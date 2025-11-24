package threads;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.random.RandomGenerator;

public class CompletableFutureExperiments {

    void main() throws InterruptedException, ExecutionException {
        try (var executor = Executors.newSingleThreadExecutor()) {
            var future = executor.submit(getRandInt());

            while (!future.isDone()) {
                IO.println("Future: " + future + " - NOT DONE");
                var r = future.state();
                IO.println("F:: " + r);
                Thread.sleep(Duration.ofSeconds(1));
            }

            var res = future.resultNow();
            IO.println("future result " + res);
            executor.shutdown();

        }

    }

    private static Callable<Integer> getRandInt() {
        return () -> {
            var randInt = RandomGenerator.getDefault().nextInt(1000);
            Thread.sleep(Duration.ofSeconds(10));
            return randInt;
        };
    }
}
