package constructors_in_java25;

import java.util.random.RandomGenerator;

public final class Volvo  implements  CarInterface {

    String volvoKey;

    public Volvo () {
        volvoKey = "VOLVO" + RandomGenerator.getDefault().nextInt(1000);
        boast();
        IO.println("Checking Volvo Ignition: " + ignitionOn(volvoKey));
    }

    @Override
    public void boast() {
        IO.println("Volvo is the BEST!");
    }
}
