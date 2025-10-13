package collections;

public class Playground {

    void main() {
        Integer i = Integer.MAX_VALUE;
        IO.println("i=[" + i + "]");
        i++;
        IO.println("i=[" + i + "]");
        if (i == Integer.MIN_VALUE) {
            IO.println("Is MIN_VALUE");
        } else {
            IO.println("more than mIN_value...");
        }
    }


}
