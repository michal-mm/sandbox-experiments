package constructors_in_java25;

public class Sandbox {

    void main() {
        IO.println("\nA a = new A()");
        A a = new A();

        String s = "b";
        String nullStr = null;

        IO.println("\ntest B b1 = new B(\"b\")");
        B b1 = new B(s);

        IO.println("\ntest B b2 = new B(null)");
        try {
            B b2 = new B(nullStr);
        } catch (NullPointerException npe) {
            IO.println("### NullPointerException: " + npe.getMessage());
        }

        IO.println("\ntest C c1 = new C(\"b\")");
        C c1 = new C(s);

        IO.println("\ntest C c2 = new C(null)");
        C c2 = new C(nullStr);
    }

    public static void main2(String[] args) {
        System.out.println("psvm test");
    }
}
