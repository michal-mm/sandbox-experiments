package threads.actionexecutor;

import java.time.Duration;

@FunctionalInterface
public interface ActionOperator {
    String apply(String action, String response, Duration sleepTime);
}