package gatherers;

import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class GathererExperiments {

    void main() {
        foldExperiments();
        mapConcurrentExperiment();
        scanExperiment();
        slidingWindowExperiment();
        customGathererExperiment();
    }

    private static void foldExperiments() {
        title("FOLD experiment");
        var resultAfterGatherList = Stream.of(1,2,3,4,5)
                .gather(Gatherers.fold(() -> 0, Integer::sum))
                .toList();

        IO.println(resultAfterGatherList);
    }

    private static void mapConcurrentExperiment() {
        title("MAP CONCURRENT experiment");

        var upperCaseList = Stream.of("java", "is", "great", "Says", "the", "DUKe")
                .gather(Gatherers.mapConcurrent(4, String::toUpperCase))
                .toList();

        IO.println(upperCaseList);
    }

    private static void scanExperiment() {
        title("SCAN experiment");

        var interimResultList = Stream.of(1,2,3,4,5)
                .gather(Gatherers.scan(() -> 100, Integer::sum))
                .toList();

        IO.println(interimResultList);
    }

    private static void slidingWindowExperiment() {
        title("SLIDING WINDOW experiment");

        var slidingWindowResultList = Stream.of(1,2,3,4,5)
                .gather(Gatherers.windowSliding(2))
                .map(GathererExperiments::sumElems)
                .toList();

        IO.println(slidingWindowResultList);
    }

    private static void customGathererExperiment() {
        title("CUSTOM GATHERER experiment");

        var v = Stream.of("java", "duke", "apple", "pineapple")
                .gather(Gatherer.of((state, element, downstream) -> {
                    IO.println("state:" + state);
                    downstream.push(element.length());
                    return true;
                }))
                .toList();

        IO.println(v);
    }

    private static int sumElems(List<Integer> nums) {
        return nums.stream().reduce(0, Integer::sum);
    }

    private static void printDashedLine() {
        IO.println("-".repeat(15));
    }

    private static void title (String msg) {
        printDashedLine();
        IO.println(msg);
        printDashedLine();
    }
}
