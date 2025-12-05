package unclassified;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerExperiments {

    void main() {
        try (var scanner = new Scanner(System.in)) {
            Pattern p = Pattern.compile("\\d+-?\\d*");
            while (scanner.hasNext(p)) {
                var i = scanner.next(p);
//                var i = scanner.nextInt();
                IO.println("-> " + i);
            }
        }
    }
}
