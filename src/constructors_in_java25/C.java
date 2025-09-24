package constructors_in_java25;

public class C extends B {

    // let's mess around! (see B definition)!
    private final String  b;

    public C (String s) {
        this.b = s;
        super(s, true);
    }
}
