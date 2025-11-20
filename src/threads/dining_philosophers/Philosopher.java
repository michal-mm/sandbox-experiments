package threads.dining_philosophers;

import java.util.Objects;
import java.util.random.RandomGenerator;

public class Philosopher extends Thread implements Runnable {

    private final String name;

    private Philosopher() {
        this.name = "Philosopher Rand#" + RandomGenerator.getDefault().nextInt(1000,2000);
    }

    public Philosopher(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public void start() {
        IO.println("Starting: " + name);
        // TODO
    }

    @Override
    public void run() {
        // TODO
    }
}
