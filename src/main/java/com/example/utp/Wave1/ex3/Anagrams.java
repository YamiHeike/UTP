package com.example.utp.Wave1.ex3;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import java.util.stream.Collectors;

public class Anagrams {

    private List<String> words = new ArrayList<>();
    private Map<String, List<String>> anagrams;

    public Anagrams(String fileName) throws FileNotFoundException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                while(st.hasMoreTokens()) {
                    String token = st.nextToken().trim().toLowerCase();
                    token = token.replaceAll("\\s+", "");
                    words.add(token);
                }
            }
            anagrams = findAnagrams();
        } catch(IOException ioe) {
            System.out.println("Could not read from the file: " + fileName);
            ioe.printStackTrace();
        }
    }

    public List<List<String>> getSortedByAnQty() {
        List<List<String>> result = new ArrayList<>();

        anagrams.forEach((k,v) -> {
            List<String> current = new ArrayList<>(v);
            current.add(k);
            Collections.sort(current);
            result.add(current);
        });


        Comparator<List<String>> sizeComp = Comparator.comparing(List::size);

        return result.stream()
                .filter(elem -> !elem.isEmpty())
                .distinct()
                .sorted(sizeComp.reversed())
                .collect(Collectors.toList());
    }


    public Map<String, List<String>> findAnagrams() {
        if (words == null || words.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<String>> anagramMap = new HashMap<>();
        for (String word : words) {
            String sortedWord = sortWord(word);

            anagramMap.computeIfAbsent(sortedWord, k -> new ArrayList<>()).add(word);
        }

        Map<String, List<String>> result = new HashMap<>();
        for (String word : words) {
            String key = sortWord(word);
            List<String> anagramsForWord = anagramMap.get(key);

            List<String> filteredAnagrams = new ArrayList<>();
            if (anagramsForWord != null) {
                filteredAnagrams = anagramsForWord.stream()
                        .filter(anagram -> !anagram.equals(word))
                        .collect(Collectors.toList());
            }
            result.put(word, filteredAnagrams);
        }

        return result;
    }


    private String sortWord(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }


    public String getAnagramsFor(String word) {
        return word + ": " + anagrams.get(word.trim().toLowerCase());
    }


}
