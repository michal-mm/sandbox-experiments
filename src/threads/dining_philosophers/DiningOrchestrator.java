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

    public boolean getCutlery(Philosopher p) {
        var idx = getIndices(p);

        var firstLock = locks.get(idx.getFirst());
        var secondLock = locks.get(idx.getLast());

        try {
            var haveFirstLock = firstLock.tryLock(3, TimeUnit.SECONDS);

            if (haveFirstLock) {
                var haveSecondLock = secondLock.tryLock(3, TimeUnit.SECONDS);
                if (haveSecondLock) {
                    return true;
                } else {
                    firstLock.unlock();
                    return false;
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            firstLock.unlock();
            secondLock.unlock();
            throw new RuntimeException(e);
        }
    }

    public void giveBackCutlery(Philosopher p) {
        var ids = getIndices(p);
        ids.forEach(i -> locks.get(i).unlock());
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
