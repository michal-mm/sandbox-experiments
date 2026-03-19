package threads.fcm;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.random.RandomGenerator;

public class ActionAdapter {

    @FunctionalInterface
    public interface ActionOperator {
        String apply(String action, String response, Duration sleepTime);
    }


    private void performAction(List<String> actions, List<String> params){
        for (var action : actions){
            var sleepTime = Duration.ofSeconds(RandomGenerator.getDefault().nextInt(3, 10));
            if (RandomGenerator.getDefault().nextBoolean()) {
                executeAction(action, params.get(RandomGenerator.getDefault().nextInt(params.size())), sleepTime, this::successfulActionWithRandomSleep);
            } else {
                executeAction(action, params.get(RandomGenerator.getDefault().nextInt(params.size())), sleepTime, this::randomFailAction);
            }
        }
    }

    private String executeAction(String action, String response, Duration sleepTimeOfSeconds, ActionOperator op){
        return op.apply(action, response, sleepTimeOfSeconds);
    }

    private String successfulActionWithRandomSleep(String action, String param, Duration sleepTimeOfSeconds) {
        // TODO
        return null;
    }

    private String randomFailAction(String action, String param, Duration sleepTimeOfSeconds) {
        // TODO
        return null;
    }
}
