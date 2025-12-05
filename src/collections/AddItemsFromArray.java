package collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testMaxInt() {
        int tab [] = {1, Integer.MAX_VALUE};
        long result = addItems(tab);
        try {
            int intFromLong = Math.toIntExact(result);
            IO.println(intFromLong);
            fail();
        } catch (ArithmeticException _) {
            assertTrue(true);
        }

        int i = 1;
        i += Integer.MAX_VALUE;
        IO.println("i=" + i);
        IO.println("min int=" + Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, i);

        long l = 1;
        l += Long.MAX_VALUE;
        IO.println(l);
        IO.println(Long.MIN_VALUE);
        assertEquals(Long.MIN_VALUE, l);
    }
}
