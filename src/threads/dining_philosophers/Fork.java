package threads.dining_philosophers;

import java.util.Objects;
import java.util.random.RandomGenerator;

public class Fork {

    private final String name;
    private String usedBy;
    private boolean inUse;

    private Fork() {
        this.name = "Fork Rand#" + RandomGenerator.getDefault().nextInt(1000, 2000);
    }

    public Fork(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public void pickUp(String who) {
        this.usedBy = who;
        inUse = true;
    }

    public void putDown() {
        this.usedBy = null;
        this.inUse = false;
    }
}
