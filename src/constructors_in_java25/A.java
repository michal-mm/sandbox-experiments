package constructors_in_java25;

public class A {
    public A() {
        IO.println(a());
    }

    public String a() {
        return "A-Calling " + this.getClass() + "::a";
    }
}
