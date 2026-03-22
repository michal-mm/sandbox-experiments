package threads.actionexecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;


public class ActionAdapter implements ActionAdapterPort {


    public List<String> performAction(List<String> actions, List<String> params){
        var result = new ArrayList<String>();

        for (var action : actions){
            var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
            if (randBoolean25vs75()) {
                result.add(executeAction(action,
                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                        sleepTime,
                        this::successfulActionWithRandomSleep));
            } else {
                result.add(executeAction(action,
                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                        sleepTime,
                        this::randomFailAction));
            }
        }

        return result;
    }

    public List<String> performActionAsync(List<String> actions, List<String> params){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var futures = actions.stream()
                    .map(action -> {
                        var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
                        return CompletableFuture.supplyAsync(() -> {
                            if (randBoolean25vs75()) {
                                return executeAction(action,
                                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                                        sleepTime,
                                        this::successfulActionWithRandomSleep);
                            } else {
                                return executeAction(action,
                                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                                        sleepTime,
                                        this::randomFailAction);
                            }
                        }, executor);
                    })
                    .toList();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        }
    }

    private static boolean randBoolean25vs75() {
        return RandomGenerator.getDefault().nextBoolean()
                || RandomGenerator.getDefault().nextBoolean();
    }

    private String executeAction(String action, String response, Duration sleepTimeOfSeconds, ActionOperator op){
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
