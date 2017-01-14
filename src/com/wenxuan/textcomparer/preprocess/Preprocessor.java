package com.wenxuan.textcomparer.preprocess;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wenxuan.textcomparer.preprocess.PreprocessUtils.isEndOfLine;
import static com.wenxuan.textcomparer.preprocess.PreprocessUtils.sortByValue;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */

public class Preprocessor{
    private static final Pattern PATTERN =
            Pattern.compile("([^a-zA-Z_]+)([a-zA-Z_]+[0-9]*[a-zA-Z_]+)([^a-zA-Z_]+)");

    /**
     * replace the identifiers with given root
     * @param s1
     * @param s2
     * @return String array representing new contents
     */
    public static String[] replaceIdentifiers(String s1, String s2) {
        int start = 0;

        Map<String, Integer> hm1 = countWord(s1);
        Map<String, Integer> hm2 = countWord(s2);

        iterateAndRemove(hm1);
        iterateAndRemove(hm2);

        List<String> list1 = sortByValue(hm1);
        List<String> list2 = sortByValue(hm2);

        String root = "root";
        while(start < list1.size() && start < list2.size()) {
            s1 = s1.replaceAll(list1.get(start),root+start);
            s2 = s2.replaceAll(list2.get(start),root+start);
            start++;
        }

        return new String[]{replace(s1),replace(s2)};

    }

    /**
     * generate HashMap that counts words
     * @param input
     * @return hashmap with word and frequency
     */
    private static Map<String, Integer> countWord(String input) {
        Map<String, Integer> hm = new HashMap<>();
        Matcher m = PATTERN.matcher(input);
        while (m.find()) {
            String newMatch = m.group(2);
            if (hm.containsKey(newMatch)) {
                hm.put(newMatch, hm.get(newMatch) + 1);
            }
            else {
                hm.put(newMatch, 1);
            }
        }
        return hm;

    }

    /**
     * iterate over the hashmap and remove infrequent entries
     * @param hm
     */
    private static void iterateAndRemove(Map<String,Integer> hm) {
        for(Iterator<Map.Entry<String, Integer>> it = hm.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            if(entry.getValue() < 2) {
                it.remove();
            }
        }
    }

    private static String replace(String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    /**
     * remvoe Java/C style comment
     * @param input
     * @return String without Java/C line comment
     */
    public static String removeComment(String input) {
        StringBuilder sb = new StringBuilder();
        int length = input.length();
        int lastCharPos = length - 1;
        for(int i = 0; i < lastCharPos; i++) {
           if(input.charAt(i) == '/' && input.charAt(i+1) == '/') {
               while(++i < length) {
                    if(isEndOfLine(input.charAt(i)))
                        break;
               }
           }
           else {
               sb.append(input.charAt(i));
           }
        }
        return sb.toString();
    }

    /**
     * remove Java/C style block comment
     * @param input
     * @return String without Java/C block comment
     */
    public static String removeBlocks(String input) {
        StringBuilder sb = new StringBuilder();
        int length = input.length();
        int lastCharPos = length - 1;
        for(int i = 0; i < lastCharPos; i++) {
            if(input.charAt(i) == '/' && input.charAt(i+1) == '*') {
                i++;
                while(++i < length)
                    if(input.charAt(i-1) == '*' && input.charAt(i) == '/')
                        break;
            }
            else{
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

}
