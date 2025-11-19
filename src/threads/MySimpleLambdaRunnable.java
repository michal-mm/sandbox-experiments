package threads;

import java.time.Duration;
import java.util.random.RandomGenerator;

public class MySimpleLambdaRunnable {

    public static Runnable getSimpleRunnable() {

        return () -> {
            var d = RandomGenerator.getDefault().nextInt(10);
            IO.println("simple Lambda-like Runnable");
            try {
                Thread.sleep(Duration.ofSeconds(d));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            IO.println("simple Lambda-like Runnable slept - " + d);
        };
    }
}
