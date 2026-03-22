package threads.actionexecutor;

import java.util.List;

public class ActionExecutor {

    void main() {
        ActionAdapter adapter = new ActionAdapter();

        var actions   = List.of("Serve", "Code", "Fail", "Return", "Smash", "Debug", "Decode", "Slam");
        var responses = List.of("Duke", "Java", "Python", "Pete", "Sampras");

        var result = adapter.performActionAsync(actions, responses);
        IO.println(result);

        LatencyMetrics metrics = adapter.getMetrics();
        IO.println("Total latency: " + metrics.totalLatency().toMillis() + " ms");
        metrics.splitLatencies().forEach((action, duration) ->
                IO.println("  " + action + ": " + duration.toMillis() + " ms"));
    }
}
