package demo;

import java.util.LinkedHashMap;

/**
 * Programmer : pancara
 * Date       : 9/17/13
 * Time       : 11:03 AM
 */
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        java.util.Map<Long, String> map = new LinkedHashMap<>();
        map.put(1L, "AAA");
        map.put(5L, "BBBB");
        map.put(100L, "CCC");
        map.put(9L, "CCC");
        map.put(2L, "CCC");
        map.put(4L, "CCC");
        map.put(3L, "CCC");
        
        for (Long i : map.keySet()) {
            System.out.println(i);
        }

    }
}
