package constructors_in_java25;

import java.util.Objects;

public class B extends A {

    private final String  b;

    public B(String s) {
        this(s, false);
    }

    public B(String s, boolean noNullCheck) {
        if (noNullCheck) {
            this.b = s;
        } else {
            this.b = Objects.requireNonNull(s);
        }
        super();
    }


    public String a() {
        return "B-Calling " + this.getClass() + "::a -> " + b;
    }
}
