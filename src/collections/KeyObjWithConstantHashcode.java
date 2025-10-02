package collections;

import java.util.Objects;

public class KeyObjWithConstantHashcode {
    private final String name;

    public KeyObjWithConstantHashcode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KeyObjWithConstantHashcode that = (KeyObjWithConstantHashcode) o;
        return Objects.equals(name, that.name);
    }

    /**
     * This method is intentionally badly implemented. It only returns
     * an acceptable result if the @name is equals to "ALL GOOD",
     * otherwise it always returns 100
     * @return 100 for all objects unless the name is "ALL GOOD"
     */
    @Override
    public int hashCode() {

        if ("ALL GOOD".equals(name))
            return Objects.hashCode(name);
        else
            return 100;
    }

    @Override
    public String toString() {
        return "[Constant hashcode]::" + name;
    }
}
