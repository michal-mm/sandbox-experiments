package constructors_in_java25;

public class Tesla extends Ford {

    private final String teslaKey = "No key in Tesla";

    public Tesla() {
        IO.println("Tesla! Tesla! ... battery running low :-(");
        boast();
        IO.println(ignitionOn(teslaKey));
    }

    public void boast() {
        IO.println("Who's that???");
        super.boast();
        IO.println("Surprise, I'm Tesla, not Ford!");
    }

    public String ignitionOn(String key) {
        return "?!@#$%^& -> Tesla running low...";
    }
}
