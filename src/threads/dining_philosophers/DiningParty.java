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

        initOrchestrator(philosophers, forks);

        IO.println("Starting the party");
        philosophers.forEach(Philosopher::start);

        // let's wait until all philosophers are done
        philosophers.forEach(philosopher -> {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        IO.println("Party is over");

        philosophers.forEach(p->IO.println(p.toString()));
    }

    private static void initOrchestrator(List<Philosopher> philosophers, List<Fork> forks) {
        new DiningOrchestrator(philosophers, forks);
    }
}
