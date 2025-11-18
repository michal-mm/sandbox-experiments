package collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddItemsFromArray {

    long addItems(int[] items) {
        var result = Arrays.stream(items)
                .mapToLong(i->i)
                .sum();

        return result;
    }

    @Test
    void simpleAddTest() {
        int[] tab = {1,2,3,4};
        assertEquals(10, addItems(tab));
    }
}
