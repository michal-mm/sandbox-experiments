package collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HashcodeAndEqualsPlayground {

    void main() {
        String[] names = {"Name #1", "ALL GOOD", "Cat", "Pete", "Marathon",
                            "Name #2", "Name #3", "Name #4", "Name #5"};

        IO.println("----------------");
        IO.println("Experimenting with Constant hashcodes");
        experimentWithConstantHahscode(names);

        IO.println("----------------");
        IO.println("Experimenting with Random hashcodes");
        experimentWithRandomHashcode(names);
    }

    /**
     * Because the hashcode() method returns random integers
     * the map gets broken. The Map.Entry set has everything
     * (key-value mapping), but getting values from the map
     * by calling get method and passing key object returns null
     * even though that the value exists in the map
     * (containsValue() returns true).
     * @param names - array with names as string
     */
    private static void experimentWithRandomHashcode(String[] names) {
        Map<KeyObjWithRandomHashcode, RandomValueObj> mapRandomHashcodes = new HashMap<>();
        KeyObjWithRandomHashcode[] keyObjRand = new KeyObjWithRandomHashcode[names.length];
        RandomValueObj[] valueObjs = new RandomValueObj[names.length];

        for (int i=0; i<names.length; i++) {
            keyObjRand[i] = new KeyObjWithRandomHashcode(names[i]);
            valueObjs[i] = new RandomValueObj(i, names[i]);
            mapRandomHashcodes.put(keyObjRand[i], valueObjs[i]);
        }
        printMapContent(mapRandomHashcodes);

        IO.println("Putting same names one more time");
        for(int i=0; i<names.length; i++) {
            mapRandomHashcodes.put(keyObjRand[i], valueObjs[i]);
        }
        printMapContent(mapRandomHashcodes);
    }

    private static void experimentWithConstantHahscode(String[] names) {
        Map<KeyObjWithConstantHashcode, RandomValueObj> mapConstantHashcodes = new HashMap<>();
        KeyObjWithConstantHashcode[] keyObjCons = new KeyObjWithConstantHashcode[names.length];
        RandomValueObj[] valueObjs = new RandomValueObj[names.length];

        for (int i = 0; i< names.length; i++) {
            keyObjCons[i] = new KeyObjWithConstantHashcode(names[i]);
            valueObjs[i] = new RandomValueObj(i, names[i]);
            mapConstantHashcodes.put(keyObjCons[i], valueObjs[i]);
        }
        printMapContent(mapConstantHashcodes);

        IO.println("Putting same names one more time");
        for (int i = 0; i< names.length; i++) {
            mapConstantHashcodes.put(keyObjCons[i], valueObjs[i]);
        }
        printMapContent(mapConstantHashcodes);
    }

    private static void printMapContent(Map<?,?> map) {
        Objects.requireNonNull(map);
        IO.println("--- Map.Entry for loop ---");
        for (Map.Entry<?,?> entry : map.entrySet()) {
            IO.println(entry.getKey() + " --> " + entry.getValue());
        }
        IO.println("--- Key iterator ---");
        for (Object key : map.keySet()) {
            IO.println(key + " --> " + map.get(key));
        }
        IO.println("--- Just Values ---");
        for (Object val : map.values()) {
            IO.println("   " + val + "  containsValue: " + map.containsValue(val));
        }
    }
}
