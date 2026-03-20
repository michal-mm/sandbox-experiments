package threads.actionexecutor;

import java.util.List;

public class ActionExecutor {

    void main() {
        ActionAdapter aa = new ActionAdapter();

        var actions = List.of("Serve", "Code", "Fail", "Return", "Smash", "Debug", "Decode", "Slam");
        var responses = List.of("Duke", "Java", "Python", "Pete", "Sampras");

        var result = aa.performActionAsync(actions, responses);
        IO.println(result);
    }
}
