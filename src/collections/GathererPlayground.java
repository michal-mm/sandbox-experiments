package collections;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class GathererPlayground {

    @Test
    void reduce_experiment1() {
        Stream<Integer> numbers = Stream.of(1,2,3,4,5);
        Integer expected = Stream.of(1,2,3,4,5).mapToInt(i->i*i).sum();
        Integer sumOfSquares = numbers.reduce(0, (result, element) ->
                result + element * element);

        assertEquals(expected, sumOfSquares);
    }

    @Test
    void reduce_experiment2() {
        var numbers = Stream.of(1,2,3,4,5);
        Optional<String> aString = numbers.map(item -> item.toString())
                .reduce((result,item) -> result + ":" + item + ":|");

        // note that item '1' is sort of special, because it won't be extra surrounded with ':|'
        // because it's coming from the default value in the reduce function
        assertEquals("1:2:|:3:|:4:|:5:|", aString.get());
    }

    @Test
    void gather_experiment1() {
        Stream<Integer> numbers = Stream.of(1,2,3,4,5);
        Stream<Integer> foldedNumbers= numbers.gather(Gatherers.fold(() -> 100, Integer::sum));
        List<Integer> resultList = foldedNumbers.toList();

        assertEquals(1, resultList.size());
        assertEquals(Integer.valueOf(100+15), resultList.getFirst());
    }

    @Test
    void gather_slidingWindow_experiment() {
        Stream<Integer> numbers = Stream.of(1,2,3,4,5);
        List<Integer> expectedList = List.of(1+2+3, 2+3+4, 3+4+5);
        List<Integer> slidingSums = numbers.gather(Gatherers.windowSliding(3))
                .map(list -> list.stream().mapToInt(item -> item).sum())
                .toList();

        assertEquals(expectedList.size(), slidingSums.size());
        assertArrayEquals(expectedList.stream().sorted().toArray(), slidingSums.stream().sorted().toArray());
    }
}
