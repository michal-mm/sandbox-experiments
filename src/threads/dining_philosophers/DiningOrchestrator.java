package threads.dining_philosophers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class DiningOrchestrator {

    private final List<Fork> forks;
    private final List<Philosopher> philosophers;

    private final List<ReentrantLock> locks = new ArrayList<>();

    public DiningOrchestrator (List<Philosopher> philosophers, List<Fork> forks) {
        Objects.requireNonNull(forks);
        Objects.requireNonNull(philosophers);
        this.forks = forks;
        this.philosophers = philosophers;

        passOrchestratorReferenceToPhilosophers();
        initLocks();
    }

    public boolean tryEat(Philosopher p , Runnable eatAction) {
        var indices = getIndices(p);
        Integer idxFirst = indices.getFirst();
        Integer idxLast = indices.getLast();

        ReentrantLock firstLock = locks.get(idxFirst);
        ReentrantLock secondLock = locks.get(idxLast);

        try {
            if (!firstLock.tryLock(3, TimeUnit.SECONDS)) {
                return false;
            }

            try {
                if (!secondLock.tryLock(3, TimeUnit.SECONDS)) {
                    return false;
                }

                try {
                    pickUpFork(idxFirst, p.name());
                    pickUpFork(idxLast, p.name());

                    eatAction.run();

                    putDownFork(idxFirst);
                    putDownFork(idxLast);

                    return true;
                } finally {
                    secondLock.unlock();
                }
            } finally {
                firstLock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while trying to acquire cutlery", e);
        }
    }

    private void pickUpFork(int forkIdx, String philosopherName) {
        var fork = forks.get(forkIdx);
        fork.pickUp(philosopherName);
        IO.println(fork);
    }

    private void putDownFork(int forkIdx) {
        var fork = forks.get(forkIdx);
        fork.putDown();
        IO.println(fork);
    }

    private void passOrchestratorReferenceToPhilosophers() {
        philosophers.forEach(p -> p.setOrchestrator(this));
    }

    private void initLocks() {
        IntStream.range(0, forks.size())
                .forEach(_ -> locks.add(new ReentrantLock()));
    }

    private List<Integer> getIndices(Philosopher p) {
        var idx = philosophers.indexOf(p);
        return List.of(idx, (idx+1)%philosophers.size());
    }
}
