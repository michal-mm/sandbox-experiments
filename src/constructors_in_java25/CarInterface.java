package constructors_in_java25;

public sealed interface CarInterface permits Ford, Toyota, Volvo {

    void boast();

    default String ignitionOn(String key) {
        return "CarInterface (" + this.getClass() + ") -- key: [" + key + "] -- IGNITION ON";
    }
}
