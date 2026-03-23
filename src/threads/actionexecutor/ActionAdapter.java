package threads.actionexecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class ActionAdapter {

    private final LatencyInterceptor interceptor;

    public ActionAdapter() {
        this.interceptor = new LatencyInterceptor(new LatencyRecorder());
    }

    public ActionAdapter(LatencyInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public List<String> performAction(List<String> actions, List<String> params) {
        var result = new ArrayList<String>();
        interceptor.startBatch();

        for (var action : actions) {
            var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
            var param = params.get(RandomGenerator.getDefault().nextInt(params.size()));
            ActionOperator op = randBoolean25vs75()
                    ? this::successfulActionWithRandomSleep
                    : this::randomFailAction;

            result.add(interceptor.recordLatency(action, () -> executeAction(action, param, sleepTime, op)));
        }

        interceptor.stopBatch();
        return result;
    }

    public List<String> performActionAsync(List<String> actions, List<String> params) {
        interceptor.startBatch();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var futures = actions.stream()
                    .map(action -> {
                        var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
                        var param = params.get(RandomGenerator.getDefault().nextInt(params.size()));
                        boolean success = randBoolean25vs75();
                        ActionOperator op = success
                                ? this::successfulActionWithRandomSleep
                                : this::randomFailAction;
                        String actionType = success ? "successfulActionWithRandomSleep" : "randomFailAction";

                        return CompletableFuture.supplyAsync(
                                () -> interceptor.recordLatency(actionType, () -> executeAction(action, param, sleepTime, op)),
                                executor);
                    })
                    .toList();

            var results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

            interceptor.stopBatch();
            return results;
        }
    }

    public LatencyMetrics getMetrics() {
        return interceptor.getMetrics();
    }

    private static boolean randBoolean25vs75() {
        return RandomGenerator.getDefault().nextBoolean()
                || RandomGenerator.getDefault().nextBoolean();
    }

    private String executeAction(String action, String response, Duration sleepTimeOfSeconds, ActionOperator op) {
        return op.apply(action, response, sleepTimeOfSeconds);
    }

    private String successfulActionWithRandomSleep(String action, String response, Duration sleepTimeOfSeconds) {
        IO.println("START: -" + action + "- SLEEP TIME: " + sleepTimeOfSeconds);
        executeSleep(sleepTimeOfSeconds);
        IO.println("END: -" + action + "- RETURNING: " + response);
        return action + "-" + response;
    }

    private String randomFailAction(String action, String response, Duration sleepTimeOfSeconds) {
        IO.println("START: ~" + action + "~ SLEEP TIME: " + sleepTimeOfSeconds);
        executeSleep(sleepTimeOfSeconds);

        if (RandomGenerator.getDefault().nextInt(100) % 3 == 0) {
            IO.println("END: ~" + action + "~ ERROR");
            return action + "-" + "ERROR";
        } else {
            IO.println("END: ~" + action + "~ RETURNING: " + response);
            return action + "-" + response;
        }
    }

    private void executeSleep(Duration sleepTimeOfSeconds) {
        try {
            Thread.sleep(sleepTimeOfSeconds);
        } catch (InterruptedException e) {
            IO.println("Interrupted: " + e.getMessage());
        }
    }
}
