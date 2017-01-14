package com.wenxuan.textcomparer.preprocess;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */

public class PreprocessUtils {

    public static final String getExtension(String input) {
        return input.substring(input.lastIndexOf("."));
    }

    public static final boolean isEndOfLine(char c) { return c == '\n' || c == '\r'; }

    /**
     * util for reading files
     * @param path
     * @param encoding
     * @return String containing file contents
     * @throws IOException
     */
    public static final String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Sort HashMap elements based on V
     * @param map
     * @param <K>
     * @param <V>
     * @return List with elements sorted in sort
     */
    public static <K, V extends Comparable<? super V>> List<K>
        sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<K, V>>(){
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });

        List<K> result = new ArrayList<>();
        for (Map.Entry<K, V> entry : list){
            result.add(entry.getKey());
        }
        return result;
    }

}
