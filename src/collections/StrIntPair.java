package collections;

import java.util.Objects;

public class StrIntPair {

    private Integer intVal;
    private String strVal;

    public StrIntPair(Integer intVal, String strVal) {
        this.intVal = intVal;
        this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StrIntPair that = (StrIntPair) o;
        return Objects.equals(intVal, that.intVal) && Objects.equals(strVal, that.strVal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intVal, strVal);
    }

    @Override
    public String toString() {
        return intVal + "-" + strVal;
    }
}
