package threads.dining_philosophers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static threads.dining_philosophers.DiningParty.initOrchestrator;

public class DiningPartyVirtualExecutorsAndFutures {

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

        IO.println("Starting the party with Virtual Threads and ExecutorService");

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<CompletableFuture<String>> futures = philosophers.stream()
                    .map(callablePh -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return callablePh.call();
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }, executorService))
                    .toList();

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.join();

            IO.println("Virtual CompletableFuture Party is over");
            futures.forEach(f -> IO.println(f.resultNow()));

            executorService.shutdown();
        }
    }
}
