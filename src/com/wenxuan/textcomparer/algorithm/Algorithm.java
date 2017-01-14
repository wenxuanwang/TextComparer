package com.wenxuan.textcomparer.algorithm;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */
public interface Algorithm {
    /**
     * Interface for algorithm
     *  @param s1 content of the first file
     *  @param s2 content of the second file
     *  @return a number representing the percentage of matches
     */
    double match(String s1, String s2);

}