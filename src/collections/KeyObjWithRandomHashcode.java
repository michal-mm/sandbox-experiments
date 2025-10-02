package collections;

import java.util.Objects;
import java.util.Random;

public class KeyObjWithRandomHashcode {
    private final String name;

    public KeyObjWithRandomHashcode (String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KeyObjWithRandomHashcode that = (KeyObjWithRandomHashcode) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Badly implemented hashcode method that returns random values.
     * It breaks the contract between equals and hashcode
     * (o1.equals(o2) will be true, but hascodes will likely be different
     * @return random int
     */
    @Override
    public int hashCode() {
        if ("ALL GOOD".equals(name))
            return Objects.hashCode(name);
        else
            return (new Random()).nextInt(10000);
    }

    @Override
    public String toString() {
        return "[Random hashcode]::" + name;
    }
}
