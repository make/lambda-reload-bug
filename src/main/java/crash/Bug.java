package crash;

import java.util.*;

/**
 * Created by markus on 30.12.2016.
 */
public class Bug {

    private static Random r = new Random();

    private static int POINTS = 1000;
    private static int DATA_SIZE = 2;

    public static void crash() {
        int[] ids = new int[POINTS];
        double[][] datas = new double[POINTS][];
        for(int id = 1; id <= POINTS; id++) {
            double[] data = new double[DATA_SIZE];
            for(int i = 0; i < DATA_SIZE; i++)
                data[i] = r.nextDouble();
            ids[id - 1] = id;
            datas[id - 1] = data;
        }
        VPTree vpTree = new VPTree(ids, datas);

        while(true) {
            double[] data = new double[DATA_SIZE];
            for (int i = 0; i < DATA_SIZE; i++)
                data[i] = r.nextDouble();
            vpTree.search(0, data, 10);
        }
    }

}
