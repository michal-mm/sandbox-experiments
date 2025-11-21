package unclassified;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FilesExperiments {

    void main() throws IOException {
        Map<String, Integer> words = new HashMap<>();
        Files.readAllLines(Path.of("src/unclassified/FilesExperiments.java"))
                .stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(str -> !str.isEmpty())
                .forEach(elem-> words.put(elem, words.getOrDefault(elem, 0)+1));

        words.forEach((key, value) -> IO.println("'" + key + "' -> " + value));

        IO.println("======");
        var frequency = Files.readAllLines(Path.of("src/unclassified/FilesExperiments.java"))
                .stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(str -> !str.isEmpty())
                .collect(Collectors.groupingBy(str -> str, Collectors.counting()));

        frequency.forEach((key, value) -> IO.println("'" + key + "' -> " + value));
    }
}
