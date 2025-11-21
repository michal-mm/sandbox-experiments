package threads.dining_philosophers;

import java.util.Objects;

public class Fork {

    private final String name;
    private String usedBy;
    private boolean inUse;

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

    public String toString() {
        if (inUse) {
            return name + " used by: " + usedBy;
        } else {
            return name + " FREE";
        }
    }
}
