package constructors_in_java25;

public non-sealed class Ford implements CarInterface {

    String fordKey = "FORD-123-OPEN-ME";

    public Ford() {
        IO.println("Ford ignition on... - failed");
        IO.println("Ford trying one more time...");
        IO.println(ignitionOn(fordKey));
    }

    @Override
    public void boast() {
        IO.println("Ford can do anything... but...");
    }
}
