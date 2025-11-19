package threads;

import java.time.Duration;
import java.util.random.RandomGenerator;

public class MySimpleRunnable implements Runnable{
    @Override
    public void run() {
        var d = RandomGenerator.getDefault().nextInt(10);
        IO.println("Starting MySimpleRunnable");
        try {
            Thread.sleep(Duration.ofSeconds(d));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        IO.println("MySimpleRunnable slept: " + d);
    }
}
