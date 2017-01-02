package crash;

public class Bug {

    public static void crash() {
        System.out.println("foo");
        Foo foo = new Foo();
        while(true)
            foo.bar(new double[]{}, 10);
    }

}
