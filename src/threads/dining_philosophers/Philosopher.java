package threads.dining_philosophers;

import java.time.Duration;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class Philosopher extends Thread implements Runnable {

    public static final int ANGRYNESS_LIMIT = 2;
    private final String name;
    private int eating;
    private int thinking;
    private int angry = 0;

    private DiningOrchestrator diningOrchestrator;


    public Philosopher(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Philosopher p) {
            return name.equals(p.name);
        } else {
            return false;
        }
    }

    public void setOrchestrator(DiningOrchestrator diningOrchestrator) {
        this.diningOrchestrator = diningOrchestrator;
    }

    @Override
    public void run() {
        Objects.requireNonNull(diningOrchestrator);
        initLimitsForEatingAndThinking();
        IO.println(name + " takes a seat, " + currentLimits());

        waiting();

        while ((eating > 0 || thinking > 0) && angry < ANGRYNESS_LIMIT) {
            if (eating > 0 && !diningOrchestrator.tryEat(this, this::performEating)) {
                    waiting();
                    angry++;
                    if (angry == ANGRYNESS_LIMIT)
                        IO.println(name + " SHAME! I'm starving, I'm leaving!");
                    continue;
            }

            thinking();
            IO.println(name + " " + currentLimits());
        }

        IO.println(name + " leaves the party, " + currentLimits());
    }

    public String showDetails() {
        return "[" + name + " " + currentLimits() + "]";
    }

    private void initLimitsForEatingAndThinking() {
        var rand = RandomGenerator.getDefault();
        eating = rand.nextInt(10, 21);
        thinking = rand.nextInt(10, 21);
    }

    private String currentLimits() {
        return "[to eat: " + eating + ", think: " + thinking + ", angry=" + angry + "]";
    }

    private void thinking() {
        var rand = RandomGenerator.getDefault();

        try {
            var d = rand.nextInt(1, 6);
            IO.println(name + " MEDITATING for " + d + " seconds");
            thinking = thinking < d ? 0 : thinking - d;
            Thread.sleep(Duration.ofSeconds(d));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private void performEating() {
        eating();
    }

    private void eating() {
        var rand = RandomGenerator.getDefault();

        try {
            var d = rand.nextInt(1, 6);
            IO.println(name + " EATING for " + d + " seconds");
            eating = eating < d ? 0 : eating - d;
            Thread.sleep(Duration.ofSeconds(d));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private void waiting() {
        var rand = RandomGenerator.getDefault();

        try {
            var d = rand.nextInt(1,6);
            IO.println(name + " will wait for " + d + " seconds...");
            Thread.sleep(Duration.ofSeconds(d));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
