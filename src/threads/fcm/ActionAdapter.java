package threads.fcm;

import java.time.Duration;
import java.util.List;
import java.util.random.RandomGenerator;

public class ActionAdapter {

    @FunctionalInterface
    public interface ActionOperator {
        String apply(String action, String response, Duration sleepTime);
    }


    private void performAction(List<String> actions, List<String> params){
        for (var action : actions){
            var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
            if (randBoolean25vs75()) {
                executeAction(action,
                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                        sleepTime,
                        this::successfulActionWithRandomSleep);
            } else {
                executeAction(action,
                        params.get(RandomGenerator.getDefault().nextInt(params.size())),
                        sleepTime,
                        this::randomFailAction);
            }
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
        IO.println("START: " + action + " SLEEP TIME: " + sleepTimeOfSeconds);
        executeSleep(sleepTimeOfSeconds);
        IO.println("END: " + action + " RETURNING: " + response);

        return response;
    }

    private String randomFailAction(String action, String response, Duration sleepTimeOfSeconds) {
        IO.println("START: " + action + " SLEEP TIME: " + sleepTimeOfSeconds);
        executeSleep(sleepTimeOfSeconds);

        if (RandomGenerator.getDefault().nextInt(100) % 3 == 0) {
            IO.println("END: " + action + " ERROR");
            return "ERROR";
        } else {
            IO.println("END: " + action + " RETURNING: " + response);
            return response;
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
