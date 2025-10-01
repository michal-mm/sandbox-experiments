package collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateKeyObjectsInMap {

    void main() {
        Integer[] numbers = {1, 2, 3, 5, 8};
        String[] strings = {"One", "Two", "Three", "Five", "Eight"};
        StrIntPair[] strIntPairs = new StrIntPair[numbers.length];
        RandomValueObj[] randomValueObjs = new RandomValueObj[numbers.length];

        Map<StrIntPair, RandomValueObj> map = new HashMap<>();

        for (int i = 0; i<numbers.length; i++) {
            StrIntPair strIntPair = new StrIntPair(numbers[i], strings[i]);
            strIntPairs[i] = strIntPair;
            RandomValueObj randomValueObj = new RandomValueObj(numbers[i], strings[i]);
            randomValueObjs[i] = randomValueObj;
            IO.println(strIntPair + " -> " + randomValueObj);
            map.put(strIntPair, randomValueObj);
        }

        printMap(map);

        // let's manipulate with objects representing keys
        strIntPairs[0].setIntVal(1000);
        strIntPairs[0].setStrVal("UPDATED");
        printMap(map);
        IO.println("CONTAINS MODIFIED: " + strIntPairs[0] + " ? " + map.containsKey(strIntPairs[0]));
        IO.println("CONTAINS FACTORY: " + new StrIntPair(numbers[0], strings[0]) + " ? "
                + map.containsKey(new StrIntPair(numbers[0], strings[0])));
        IO.println("MODIFIED:: " + map.get(strIntPairs[0]) + " --> " + randomValueObjs[0]);
        IO.println("FROM FACTORY:: " + map.get(new StrIntPair(numbers[0], strings[0]))
                + " --> " +  randomValueObjs[0]);
        IO.println("EXPLICIT null:: " + null
                + " --> " +  randomValueObjs[0]);

        for (StrIntPair item : map.keySet()) {
            if (Objects.equals(item, strIntPairs[0])) {
                IO.println("We found the key in the map! " + item);
            }
        }

    }

    private static void printMap(Map<StrIntPair, RandomValueObj> map) {
        IO.println("--- MAP CONTENT ---");
        map.entrySet().stream()
                .map(entry ->
                        "MAP:: " + entry.getKey() + " --> "
                                + entry.getValue()).forEach(IO::println);
    }
}
