package com.wenxuan.textcomparer.algorithm;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */
public class CosineSimilarityAlgorithm implements Algorithm {
    private static final Pattern p = Pattern.compile("([^a-zA-Z_]+)([a-zA-Z_]+[0-9]*)([^a-zA-Z_]+)");

    /**
     * Match contents and return a value representing similarity
     * @param s1 content of the first file
     * @param s2 content of the second file
     * @return cosine value
     */
    @Override
    public double match(String s1, String s2) {
        List<String> wordList = new ArrayList<>();
        Matcher m = p.matcher(s1);
        while (m.find()) {
            wordList.add(m.group(2));
        }
        List<String> s1Content = wordList;

        wordList = new ArrayList<>();
        m = p.matcher(s2);
        while (m.find()) {
            wordList.add(m.group(2));
        }
        List<String> s2Content = wordList;

        return this.getCosine(this.toMap(s1Content), this.toMap(s2Content));
    }

    /**
     * Count frequency of elements in List
     * @param wordList List of words
     * @return Map with String and frequency
     */
    private Map<String, Integer> toMap(List<String> wordList) {
        HashMap<String, Integer> hm = new HashMap<> ();
        for(String s : wordList) {
            if(hm.containsKey(s))
                hm.put(s, hm.get(s) + 1);
            else
                hm.put(s, 1);
        }
        return hm;
    }

    private double getSum(Collection<Integer> collection) {
        return collection.stream().reduce(0, (result, x) -> result + x * x);
    }

    /**
     * theta = dotproduct(A, B) / norm(A) * norm(B)
     * @param termFreq1 term frequency, refer to wikipedia: cosine similarity
     * @param termFreq2 term frequency
     * @return cosine value
     */
    private double getCosine(Map<String, Integer> termFreq1, Map<String, Integer> termFreq2) {
        double dp = termFreq1.keySet()
                .parallelStream()
                .filter(key -> termFreq2.containsKey(key))
                .collect(Collectors.summarizingDouble(key -> termFreq1.get(key) * termFreq2.get(key)))
                .getSum();

        return dp / Math.sqrt(this.getSum(termFreq1.values()) * this.getSum(termFreq2.values()));
    }

}
