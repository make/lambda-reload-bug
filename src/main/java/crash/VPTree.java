package crash;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Markus Paaso on 26.9.2016.
 * See http://stevehanov.ca/blog/index.php?id=130
 */
public class VPTree implements Serializable {

    class Point implements Comparable<Point>, Serializable {
        public int id;
        public double[] data;
        public double distance = 0.0d;

        Point(int id, double[] data) {
            this.id = id;
            this.data = data;
        }

        public double distance(Point p) {
            double dist = 0;
            for(int i = 0; i < data.length; i++)
                dist += (data[i] - p.data[i]) * (data[i] - p.data[i]);
            return p.distance = Math.sqrt(dist);
        }

        public Comparator<Point> toComparator() {
            return Comparator.comparingDouble(p -> distance(p));
        }

        public int compareTo(Point o) {
            return Integer.compare(id, o.id);
        }

        public int hashCode() {
            return id;
        }
    }

    class Node implements Serializable {
        public Point point;
        public Node left, right;
        public double threshold;

        Node(Point data) {
            this.point = data;
        }
    }

    Node root;

    public VPTree(int[] ids, double[][] data) {
        List<Point> points = new ArrayList<Point>();
        for(int i = 0; i < ids.length; i++)
            points.add(new Point(ids[i], data[i]));
        root = buildTree(points);
    }

    Random random = new Random();

    private Node buildTree(List<Point> data) {
        if(data.isEmpty())
            return null;

        Node n = new Node(data.remove(random.nextInt(data.size())));
        if(data.isEmpty())
            return n;

        Ordering<Point> order = Ordering.from(n.point.toComparator());
        int lowerPoints = (data.size() + 1) / 2;
        List<Point> lower = new ArrayList<Point>(order.leastOf(data, lowerPoints));
        Set<Point> lowerSet = new HashSet<Point>(lower);
        Set<Point> upperSet = new HashSet<Point>();
        for(Point p : data) {
            if(!lowerSet.contains(p))
                upperSet.add(p);
        }

        n.threshold = n.point.distance(lower.get(lower.size() - 1));
        n.left = buildTree(lower);
        n.right = buildTree(new ArrayList<Point>(upperSet));
        return n;
    }

    public Collection<Point> search(int id, double[] data, int k) {
        Point p = new Point(id, data);
        MinMaxPriorityQueue knn = MinMaxPriorityQueue.orderedBy(p.toComparator())
                .expectedSize(k)
                .maximumSize(k)
                .create();
        synchronized (this) {
            search(root, p, knn, k);
        }
        return knn;
    }

    private void search(Node n, Point p, MinMaxPriorityQueue<Point> knn, int k) {
        if(n == null)
            return;
        knn.add(n.point);
        if(n.left == null && n.right == null)
            return;
        if(n.point.distance < n.threshold) {
            search(n.left, p, knn, k);
            if(n.point.distance + knn.peekLast().distance >= n.threshold)
                search(n.right, p, knn, k);
        } else {
            search(n.right, p, knn, k);
            if(n.point.distance - knn.peekLast().distance <= n.threshold)
                search(n.left, p, knn, k);
        }
    }
}
