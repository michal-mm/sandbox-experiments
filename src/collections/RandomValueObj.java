package collections;

import java.util.Random;

public class RandomValueObj {

    private Integer intVal;
    private String strVal;
    private final Integer randInt;

    public RandomValueObj(Integer intVal, String strVal) {
        this.intVal = intVal;
        this.strVal = strVal;
        this.randInt = (new Random()).nextInt(1000);
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    @Override
    public String toString() {
        return intVal + "-" + strVal + "-" + randInt;
    }
}
