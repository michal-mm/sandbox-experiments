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
        IO.println("Starting the party");
        philosophers.forEach(Philosopher::start);

        philosophers.forEach(philosopher -> {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        IO.println("Party is over");
    }
}
