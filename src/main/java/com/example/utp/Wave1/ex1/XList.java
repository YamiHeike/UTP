package com.example.utp.Wave1.ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XList<T> extends ArrayList<T> implements List<T> {
    

    public XList() {
        super();
    }

    public XList(T... elems) {
        super(Arrays.asList(elems));
    }

    public XList(Collection<T> elems) {
        super(elems);
    }

    public static <T> XList<T> of(Collection<T> elems) {
        return new XList<T>(elems);
    }

    @SafeVarargs
    public static <T> XList<T> of(T... elems) {
        return new XList<T>(elems);
    }

    //Metody wytwórcze dla klas napisowych

    public static XList<String> charsOf(String text) {
        List<String> chars =  text.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        return new XList<String>(chars);
    }

    public static XList<String> tokensOf(String text) {
        StringTokenizer tokens = new StringTokenizer(text);
        XList<String> list = new XList<>();
        list.ensureCapacity(tokens.countTokens());
        while(tokens.hasMoreTokens()) {
            list.add(tokens.nextToken());
        }
        return list;
    }

    public static XList<String> tokensOf(String text, String sep) {
        StringTokenizer tokens = new StringTokenizer(text, sep);
        XList<String> list = new XList<String>();
        list.ensureCapacity(tokens.countTokens());

        while(tokens.hasMoreTokens()) {
            list.add(tokens.nextToken());
        }
        return list;
    }

    //Dodatkowe metody operacji na listach

    public XList<T> union(Collection<T> col) {
        if(col == null) {
            return new XList<T>(this);
        }
        return new XList<T>(Stream.concat(this.stream(), col.stream()).collect(Collectors.toList()));

    }

    @SafeVarargs
    public final XList<T> union(T... elems) {
        XList<T> list = new XList<>(Arrays.asList(elems));
        List<T> union = Stream.concat(this.stream(), list.stream()).collect(Collectors.toList());
        return new XList<>(union);
    }

    public XList<T> diff(Collection<T> col) {
        if(col == null) {
            return new XList<T>(this);
        }
        List<T> diff = stream().filter(elem -> !col.contains(elem)).collect(Collectors.toList());

        return new XList<>(diff);
    }

    public XList<T> unique() {
        Set<T> set = new LinkedHashSet<>(this);
        return new XList<T>(set);
    }



    public XList<XList<T>> combine() {
        if (this.isEmpty()) return new XList<>();
        List<XList<T>> product = new ArrayList<>();


        int totalCombinations = this.stream()
                .map(list -> ((List<T>) list).size())
                .reduce(1, (a, b) -> a * b);

        for (int i = 0; i < totalCombinations; i++) {
            XList<T> combination = new XList<>();

            int index = i;
            for (Object listObj : this) {
                List<T> list = (List<T>) listObj;
                combination.add(list.get(index % list.size()));
                index /= list.size();
            }

            product.add(combination); // Add the generated combination to the result
        }

        return new XList<>(product);
    }

    public <R> XList<R> collect(Function<? super T, ? extends R> function) {
        return this.stream()
                .map(function)
                .collect(Collectors.toCollection(XList::new));
    }


    public String join(String sep) {
        if(isEmpty()) return "";

        return stream().map(String::valueOf).collect(Collectors.joining(sep));
    }

    public String join() {
        if(isEmpty()) return "";

        return stream().map(String::valueOf).collect(Collectors.joining());
    }

    public void forEachWithIndex(BiConsumer<T, Integer> cons) {
        for (int i = 0; i < this.size(); i++) {
            T obj = get(i);
            cons.accept(obj, i);
        }
    }

}

