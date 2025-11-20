package threads.dining_philosophers;

import java.util.List;

public class DiningParty {

    void main() {
        var forks = List.of(new Fork("Fork #1"),
                new Fork("Fork #2"),
                new Fork("Fork #3"),
                new Fork("Fork #4"),
                new Fork("Fork #5")
        );

        var philosophers = List.of(
                new Philosopher("Philosopher #1"),
                new Philosopher("Philosopher #2"),
                new Philosopher("Philosopher #3"),
                new Philosopher("Philosopher #4"),
                new Philosopher("Philosopher #5")
        );

        var orchestrator = new DiningOrchestrator(philosophers, forks);
        orchestrator.startTheParty();
    }
}
