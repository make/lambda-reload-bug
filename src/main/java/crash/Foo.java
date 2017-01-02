package crash;

import com.google.common.collect.MinMaxPriorityQueue;

public class Foo {

    class Sub {
        Sub(double[] data) {}
    }

    public Foo() {}

    public void bar(double[] data, int size) {
        new Sub(data);
        MinMaxPriorityQueue.expectedSize(size);
    }

}
