package threads.dining_philosophers;

import java.util.List;

import static threads.dining_philosophers.DiningParty.initOrchestrator;

public class DiningPartyVirtual {

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

        IO.println("Starting the party with Virtual Threads");

        // start the threads (philosophers and let them eat and meditate)
        var philosophersThreads = philosophers.stream()
                .map(p -> Thread.ofVirtual()
                        .name(p.name())
                        .start(p)
                )
                .toList();

        // let's wait until all philosophers are done
        philosophersThreads.forEach(philosopher -> {
            try {
                IO.println(philosopher.getName() + " - is Virtual: " + philosopher.isVirtual());
                philosopher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        IO.println("Party is over");

        philosophers.forEach(p->IO.println(p.showDetails()));
        for (int i=0; i<philosophers.size(); i++) {
            IO.println(philosophers.get(i).showDetails() + " - " +
                    philosophersThreads.get(i).getName() +
                    " is Virtual: " + philosophersThreads.get(i).isVirtual());
        }
    }
}
