package collections;

import java.util.HashSet;
import java.util.Set;

public class HashSetExperiments {

    void main() {
        Set<Integer> ints = new HashSet<>(/*78184525*/);

        for (int i=0; i<Integer.MAX_VALUE; i++) {
            try {
                if (i >= 78_000_000)
                    IO.println(i);
                ints.add(i);
            } catch (Exception e) {
                IO.println("Exception for <" + i + ">");
                IO.println(e);
            }
        }

        IO.println("Set size: " + ints.size());
    }
}
