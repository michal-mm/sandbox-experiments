package strings;

public class StringExperiments {

    private static String shout(String input) {
        return "!!! " + input.toUpperCase() + " !!!";
    }

    void main() {
        String s = "hello world";
        var shouted = s.transform(StringExperiments::shout);
        IO.println(shouted);
        IO.println(s);

        IO.println("=======");
        String longString = """
                This is a block of text
                spread across several lines.
                It would be really painful to
                call specific methods right after
                this string (unless you
                call the variable name directly),
                hence we will use transform method!
                """;

        var shoutedLong = longString.transform(StringExperiments::shout);
        IO.println(shoutedLong);
    }
}
