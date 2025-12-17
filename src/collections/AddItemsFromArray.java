package collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class AddItemsFromArray {

    long addItems(int[] items) {
        return Arrays.stream(items)
                .mapToLong(i->i)
                .sum();
    }

    long addItemsParallel(int[] items) {
        return Arrays.stream(items)
                .parallel()
                .mapToLong(i -> i)
                .sum();
    }

    long addItemsForLoop(int[] items) {
        long result = 0L;
        for(var item : items) {
            result += item;
        }

        return result;
    }

    void main() {
        var intArray = IntStream.range(0, 1_000_000_000)
                .toArray();

        var startReg = System.currentTimeMillis();
        var resultReg = addItems(intArray);
        var finishReg = System.currentTimeMillis();
        IO.println("Regular - Execution time: " + (finishReg-startReg) + "  result=" + resultReg);

        var startP = System.currentTimeMillis();
        var resultP = addItemsParallel(intArray);
        var finishP = System.currentTimeMillis();
        IO.println("Parallel - Execution time: " + (finishP-startP) + "  result=" + resultP);

        var startF = System.currentTimeMillis();
        var resultF = addItemsForLoop(intArray);
        var finishF = System.currentTimeMillis();
        IO.println("For Loop - Execution time: " + (finishF-startF) + "  result=" + resultF);
    }


    @Test
    void simpleAddTest() {
        int[] tab = {1,2,3,4};
        assertEquals(10, addItems(tab));
    }

    @Test
    void testMaxInt() {
        int[] tab = {1, Integer.MAX_VALUE};
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
