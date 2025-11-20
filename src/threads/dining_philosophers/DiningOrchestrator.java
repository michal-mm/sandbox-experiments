package threads.dining_philosophers;

import java.util.List;
import java.util.Objects;

public class DiningOrchestrator {

    private final List<Fork> forks;
    private final List<Philosopher> philosophers;

    public DiningOrchestrator (List<Philosopher> philosophers, List<Fork> forks) {
        Objects.requireNonNull(forks);
        Objects.requireNonNull(philosophers);
        this.forks = forks;
        this.philosophers = philosophers;
    }

    public void startTheParty() {
        philosophers.forEach(Philosopher::start);

        IO.println("Party is over");
    }
}
