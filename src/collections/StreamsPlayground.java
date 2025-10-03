package collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"StreamToLoop", "SimplifyStreamApiCallChains"})
public class StreamsPlayground {

    void simpleMapExample() {
        List<String> countries = List.of("Poland", "Germany", "France", "Great Britain", "Belgium", "Italy", "Spain");

        // print countries
        IO.println("-----------");
        IO.println("Countries:");
        countries.stream()
                .forEach(IO::println);

        // print countries as numbered list
        AtomicInteger idx = new AtomicInteger(1);
        IO.println("-----------");
        IO.println("Countries numbered:");
        countries.stream()
                .forEach(country -> IO.println(idx.getAndIncrement() + ". " + country));

        // print countries sorted alphabetically
        IO.println("-----------");
        IO.println("Countries sorted:");
        countries.stream()
//                .parallel()
                .sorted()
//                .forEachOrdered(IO::println);
                .forEach(IO::println);

        // print countries sorted alphabetically and numbered
        IO.println("-----------");
        IO.println("Countries sorted:");
        idx.set(1);
        countries.stream()
//                .parallel()
                .sorted()
//                .forEachOrdered(country -> {
//                    IO.println(idx.getAndIncrement() + ". " + country);
//                });
                .forEach(country -> IO.println(idx.getAndIncrement() + ". " + country));
    }

    void moreComplexMapExample() {
        List<String> countries = List.of("Poland", "Germany", "France",
                "Great Britain", "Belgium", "Indonesia", "Italy", "Spain");

        // print countries
        IO.println("-----------");
        IO.println("Countries:");
        countries.stream()
                .forEach(IO::println);

        // generate the list of lengths of countries
        List<Integer> lenghts = countries.stream()
                .map(String::length)
                .toList();
        // but this ^ list may have different order than the list of countries...
        // so let's create the mapping when processing the stream
        List<NameAndLength> nameAndLength = countries.stream()
                .map(country -> new NameAndLength(country, country.length()))
                .toList();

        IO.println("-----------");
        IO.println("Countries and lengths:");
        nameAndLength.forEach(nal->IO.println(nal.name + "-" + nal.length));

        // sort alphabetically
        IO.println("-----------");
        IO.println("Countries and lengths (sorted alphabetically):");
        nameAndLength.stream()
                .sorted(Comparator.comparing(nal -> nal.name))
                .forEach(IO::println);

        // sort based on length
        IO.println("-----------");
        IO.println("Countries and lengths (sorted by length):");
        nameAndLength.stream()
                .sorted(Comparator.comparing(nal -> nal.length))
                .forEach(IO::println);

        // sort based alphabetically and the by length
        IO.println("-----------");
        IO.println("Countries and lengths (sorted alphabetically and then by length):");
        nameAndLength.stream()
                .sorted((nal1, nal2) ->
                        nal1.name.compareTo(nal2.name) == 0
                        ? nal1.length.compareTo(nal2.length)
                        : nal1.name.compareTo(nal2.name))
                .forEach(IO::println);

        // sort based on length and then alphabetically
        IO.println("-----------");
        IO.println("Countries and lengths (sorted by length and then alphabetically):");
        nameAndLength.stream()
                .sorted((nal1, nal2) ->
                        nal1.length.compareTo(nal2.length) == 0
                        ? nal1.name.compareTo(nal2.name)
                        : nal1.length.compareTo(nal2.length))
                .forEach(IO::println);
    }

    void mapAndFlatMapExample() {
        Map<String, List<String>> countries = Map.of("Poland", List.of("Warsaw", "Gdansk", "Krakow"),
                "Germany", List.of("Frankfurt", "Berlin", "Aachen", "Mainz"),
                "France", List.of("Paris", "Nice"),
                "Great Britain", List.of("London", "Bristol", "Manchester"),
                "Belgium", List.of("Brussels"),
                "Indonesia", List.of("Jakarta"),
                "Italy", List.of("Rome", "Nappoli", "Palermo", "Bergamo"),
                "Spain", List.of("Madrid", "Barcelona", "Sevilla", "Granada"));

        // print countries and cities
        IO.println("-----------------");
        IO.println("Countries and cities:");
        countries.entrySet().stream()
                .forEach(set -> {
                    IO.print(set.getKey() + ": {");
                    set.getValue().forEach(city -> IO.print(city+" "));
                    IO.println("}");
                });

        // make a flat list from the map
        IO.println("-----------------");
        IO.println("Flat list of Countries and cities:");
        countries.entrySet().stream()
                .flatMap(entrySet -> {
                    List<String> newList = new ArrayList<>();
                    newList.add("~~~~~~~~");
                    newList.add(entrySet.getKey() + ":");
                    newList.addAll(entrySet.getValue());
                    return newList.stream();
                })
                .forEach(IO::println);

        // flat list of cities only
        IO.println("-----------------");
        IO.println("Flat list of Cities:");
//        countries.entrySet().stream()
//                .flatMap(entry -> entry.getValue().stream())
//                .forEach(IO::println);
        countries.values().stream()
                .flatMap(List::stream)
                .forEach(IO::println);
    }

    private static void arraysAsListExperiments() {
        String[] strs = {"1", "a", "abc", "QWERTY"};
        List<String> fromArray = Arrays.asList(strs);
        IO.println("*********");
        IO.println("strs array:");
        Arrays.stream(strs).forEach(IO::println);
        IO.println("*********");
        IO.println("strs List:");
        fromArray.forEach(IO::println);
        // let's modify the array
        fromArray.set(0, "NEW ITEM");
        IO.println("*********");
        IO.println("strs array:");
        Arrays.stream(strs).forEach(IO::println);
        IO.println("*********");
        IO.println("strs List:");
        fromArray.forEach(IO::println);
    }

    void mapMultiExamples() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        double percentage = .01;
        List<Double> evenDoubles = integers.stream()
                .<Double>mapMulti(
                        (integer, consumer) -> {
                            if(integer % 2 == 0) {
                                consumer.accept((double)integer * (1+percentage));
                            }
                        })
                .toList();

        IO.println("-------");
        IO.println("Even doubles:");
        evenDoubles.stream().forEach(IO::println);

        IO.println("-------");
        IO.println("Names.length>3:");
        Set<String> names = Set.of("John", "Bob", "Alice", "Jane", "Frank");
        names.stream()
                .<String>mapMulti( (name, consumer) -> {
                    if(name.length()>3) {
                        consumer.accept(name + "__" + name.length());
                    }
                })
                .forEach(IO::println);

        Map<String, List<String>> countries = Map.of("Poland", List.of("Warsaw", "Gdansk", "Krakow"),
                "Germany", List.of("Frankfurt", "Berlin", "Aachen", "Mainz"),
                "France", List.of("Paris", "Nice"),
                "Great Britain", List.of("London", "Bristol", "Manchester"),
                "Belgium", List.of("Brussels"),
                "Indonesia", List.of("Jakarta"),
                "Italy", List.of("Rome", "Nappoli", "Palermo", "Bergamo"),
                "Spain", List.of("Madrid", "Barcelona", "Sevilla", "Granada"));

        // create flat map of cities using mapMulti
        // we won't have to create streams for each country (which we did earlier)
        countries.entrySet().stream()
                .mapMulti((entrySet, consumer) -> {
                    for (String city : entrySet.getValue()) {
                        consumer.accept(entrySet.getKey() + "-" + city);
                    }
                })
                .forEach(IO::println);
    }

    void main() {
        arraysAsListExperiments();
        simpleMapExample();
        moreComplexMapExample();
        mapAndFlatMapExample();
        mapMultiExamples();
    }

    record NameAndLength(String name, Integer length){
        @Override
        public String toString() {
            return name + "-" + length;
        }
    }
}
