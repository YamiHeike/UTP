package com.example.utp.Wave1.ex1;


import java.util.*;

// File Main.java can be modified
// Final grade depends on the functionality of XList's methods

@SuppressWarnings("rawtypes")
public class Main {
    public static void main(String[] args) {
        // Additional resources
        Integer[] ints = { 100, 200, 300 };
        Set<Integer> set = new HashSet<>(Arrays.asList(3, 4, 5));

        // Construction
        XList<Integer> list1 = new XList<>(1, 3, 9, 11);
        XList<Integer> list2 = XList.of(5, 6, 9);
        XList<Integer> list3 = new XList(ints);
        XList<Integer> list4 = XList.of(ints);
        XList<Integer> list5 = new XList(set);
        XList<Integer> list6 = XList.of(set);

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
        System.out.println(list5);
        System.out.println(list6);

        // Helper methods
        XList<String> slist1 = XList.charsOf("ala ma kota");
        XList<String> slist2 = XList.tokensOf("ala ma kota");
        XList<String> slist3 = XList.tokensOf("A-B-C", "-");

        System.out.println(slist1);
        System.out.println(slist2);
        System.out.println(slist3);

        // Union
        List<Integer> m1 = list1.union(list2);  // Should be assignable to List
        System.out.println(m1);
        // All methods of List interface can be performed on XList
        m1.add(11);
        System.out.println(m1);
        XList<Integer> m2 = (XList<Integer>) m1;
        XList<Integer> m3 = m2.union(ints).union(XList.of(4, 4));
        System.out.println(m2); // m2 is not modified
        System.out.println(m3); // the result is stored in m3
        m3 = m3.union(set);
        System.out.println(m3);

        // Now, the opposite of union
        // Method diff should work on any collection
        System.out.println(m3.diff(set));  // m3 elements that are not in set
        System.out.println(XList.of(set).diff(m3)); // set elements thar are not in m3

        // Method unique - returns a new list with no duplicates
        XList<Integer> uniq = m3.unique(); // it has to be a list, not a set
        System.out.println(uniq);

        // Combinations (order matters)
        List<String> sa = Arrays.asList( "a", "b");
        List<String> sb = Arrays.asList( "X", "Y", "Z" );
        XList<String> sc = XList.charsOf( "12" );
        XList toCombine = XList.of(sa, sb, sc);
        System.out.println(toCombine);
        XList<XList<String>> cres = toCombine.combine();
        System.out.println(cres);

        // collect i join
        XList<String> j1 = cres.collect(XList::join);
        System.out.println(j1.join(" "));
        XList<String> j2 =cres.collect( list -> list.join("-"));
        System.out.println(j2.join(" "));

        // forEachWithIndex
        XList<Integer> lmod = XList.of(1,2,8, 10, 11, 30, 3, 4);
        lmod.forEachWithIndex( (e, i) -> lmod.set(i, e*2));
        System.out.println(lmod);
        lmod.forEachWithIndex( (e, i) -> { if (i % 2 == 0) lmod.remove(e); } );
        System.out.println(lmod);
        lmod.forEachWithIndex( (e, i) -> { if (i % 2 == 0) lmod.remove(i); } );
        System.out.println(lmod);
    }
}

