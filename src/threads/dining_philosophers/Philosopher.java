package threads.dining_philosophers;

import java.time.Duration;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class Philosopher extends Thread implements Runnable {

    private final String name;
    private int eating;
    private int thinking;

    private Philosopher() {
        this.name = "Philosopher Rand#" + RandomGenerator.getDefault().nextInt(1000,2000);
    }

    public Philosopher(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public void run() {
        initLimitsForEatingAndThinking();
        IO.println(name + " takes a seat, " + currentLimits());

        var rand = RandomGenerator.getDefault();
        try {
            Thread.sleep(Duration.ofSeconds(rand.nextInt(1,6)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        IO.println(name + " leaves the party, " + currentLimits());
    }

    private void initLimitsForEatingAndThinking() {
        var rand = RandomGenerator.getDefault();
        eating = rand.nextInt(5, 25);
        thinking = rand.nextInt(5, 25);
    }

    private String currentLimits() {
        return "left to eat: " + eating + ", think: " + thinking;
    }
}
