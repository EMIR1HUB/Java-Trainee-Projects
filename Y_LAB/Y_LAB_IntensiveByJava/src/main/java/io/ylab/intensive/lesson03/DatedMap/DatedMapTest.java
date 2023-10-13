package io.ylab.intensive.lesson03.DatedMap;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("First", "Elephant");
        datedMap.put("Second", "Hors");
        datedMap.put("Third", "Dog");

        System.out.println("==Method get==");
        System.out.println(datedMap.get("First"));
        System.out.println(datedMap.get("Second"));
        System.out.println(datedMap.get("NON"));

        System.out.println("\n==Method containsKey==");
        System.out.println(datedMap.containsKey("First"));
        System.out.println(datedMap.containsKey("Second"));
        System.out.println(datedMap.containsKey("NON"));

        System.out.println("\n==Method keySet==");
        System.out.println(datedMap.keySet());

        datedMap.remove("Second");

        System.out.println("\n==Method getKeyLastInsertionDate==");
        System.out.println(datedMap.getKeyLastInsertionDate("First"));
        System.out.println(datedMap.getKeyLastInsertionDate("Second")); //null
        System.out.println(datedMap.getKeyLastInsertionDate("Third"));

        System.out.println("\n==Check Current Data==");
        System.out.println(datedMap);
    }
}
