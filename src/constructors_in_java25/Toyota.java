package constructors_in_java25;

import java.util.UUID;

public final class Toyota implements CarInterface{

    private final String toyotaKey;

    public Toyota () {
        toyotaKey = "TOYOTA-" + UUID.randomUUID();
        IO.println("TOYOTA: " + ignitionOn(toyotaKey));
    }
    @Override
    public void boast() {
        IO.println("Toyota is always ON");
    }

    @Override
    public String ignitionOn(String key) {
        return CarInterface.super.ignitionOn(key);
    }
}
