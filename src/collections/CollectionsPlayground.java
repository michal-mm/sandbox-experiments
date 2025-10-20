package collections;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NewClassNamingConvention")
public class CollectionsPlayground {

    void main() {
        List<Integer> numbers = List.of(1,2,3,4,5,4,3,2,1);
        Collection<Integer> collection = Collections.checkedCollection(numbers, Integer.class);

        collection.stream()
                .filter(item -> item <= 3)
                .forEach(IO::println);
    }

    @Test
    void givenList_whenNullValueAdded_doesNotFail() {
        // Line below causes NPE, because elements for List.of() API can't be null.
        // List<Integer> numbers = List.of(null, 1, 2, 3, null, 4, 5);
        Integer[] numberArray = {null, 1, 2, 3, null, 4, 5};
        // this way one can insert null items into the list
        List<Integer> numbers = Arrays.asList(numberArray);
        // check that there are 2 null values in the list
        assertEquals(2, countNulls.apply(numbers));

        // check that the 0-th is null indeed
        Integer nullNumber = numbers.getFirst();
        assertNull(nullNumber);
        // and verify that we would get NPE when doing anything with tha object
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> nullNumber.toString());
    }

    @Test
    void givenSet_whenNullValueAdded_doesNotFail() {
        Integer[] numberArray = {null, 1, 2, 3, null, 4, 5};
        Set<Integer> numbers = new HashSet<>(Arrays.asList(numberArray));
        // check that there is only 1 null value in the list
        assertEquals(1, countNulls.apply(numbers));

        // check that we can find "null" in the set
        assertTrue(numbers.contains(null));
        // and verify that we would get NPE when doing anything with tha object
        //noinspection ResultOfMethodCallIgnored
        assertThrows(NullPointerException.class, () -> numbers.forEach(Object::toString));
    }

    @Test
    void givenTreeSet_whenNullValueAdded_itFails() {
        Integer[] numberArray = {null, 1, 2, 3, null, 4, 5};
        // will fail, because one can't add null values into the Tree
        // (because objects have to be comparable, null isn't)
        assertThrows(NullPointerException.class, () -> new TreeSet<>(Arrays.asList(numberArray)));
    }

    @Test
    void givenHashMap_whenNullValueAdded_itWorksButGeneratesMess() {
        Map<Integer, Integer> numbers = new HashMap<>();
        Integer[] numberArray = {null, 1, 2, 3, null, 5, 6, null, 6};

        for(int i=0; i<numberArray.length; i++) {
            numbers.put(numberArray[i], i);
        }
        // last null that was added comes from idx=7;
        assertEquals(7, numbers.get(null));
        assertEquals(8, numbers.get(6));

        for (Entry<Integer, Integer> entry : numbers.entrySet()) {
            IO.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    @Test
    void givenHashMap_whenNullValueAdded_itWorksButGeneratesMessV2() {
        Map<Integer, Integer> numbers = new HashMap<>();
        Integer[] numberArray = {null, 1, 2, 3, null, 5, 6, null, 6};

        for(int i=0; i<numberArray.length; i++) {
            numbers.put(numberArray[i], numbers.getOrDefault(numberArray[i], i));
        }
        // first null that was added comes from idx=0;
        // all other attempts to put, will keep using the first occurrence
        assertEquals(0, numbers.get(null));
        assertEquals(6, numbers.get(6));

        for (Entry<Integer, Integer> entry : numbers.entrySet()) {
            IO.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    @Test
    void givenTreeMap_whenNullKeyAdded_fails() {
        Map<Integer, Integer> numbers = new TreeMap<>();

        // we expect to fail when adding null as key; value can be anything
        assertThrows(NullPointerException.class, () -> numbers.put(null, null));
        assertThrows(NullPointerException.class, () -> numbers.put(null, 5));

        // adding null as a value will work
        assertDoesNotThrow(() -> numbers.put(1, null));
        assertNull(numbers.get(1));

        // adding non-null key&value works
        assertDoesNotThrow(() -> numbers.put(1,2));
        assertEquals(2, numbers.get(1));
    }

    @Test
    void characterHistogram() {
        List <Character> lChars = List.of('a', 'b', 'c', 'a', 'b', 'a');

        var mapChar2Int = lChars.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting())
                );

        //noinspection rawtypes
        for (Entry entry : mapChar2Int.entrySet()) {
            IO.println(entry.getKey() + " - " + entry.getValue());
        }

        assertEquals(3, mapChar2Int.get('a'));
        assertEquals(2, mapChar2Int.get('b'));
        assertEquals(1, mapChar2Int.get('c'));
    }

    private static final Function<Collection<?>, Long> countNulls =
        collection ->
                collection.stream()
                        .filter(Objects::isNull)
                        .count();
}
