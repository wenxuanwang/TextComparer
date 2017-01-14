package com.wenxuan.textcomparer;

import com.wenxuan.textcomparer.comparer.TextComparer;
import com.wenxuan.textcomparer.preprocess.PreprocessUtils;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */
public class Main {

    private static String type = "-t";

    /**
     * Main method used for comparing texts
     * commands Supported:
     *      -t comparing texts,
     *      -c comparing Java/C codes
     *
     * @param args
     * @return nothing
     *
     */
    public static void main(String[] args) {
        if(args.length > 0)
            type = args[0];
        String file1 = args.length == 3 ? args[1] : "";
        String file2 = args.length == 3 ? args[2] : "";

	    if(! PreprocessUtils.getExtension(file1).equals(PreprocessUtils.getExtension(file2))) {
            System.out.println("File type not matched");
        }

        System.out.printf("Match: %.5f", TextComparer.calculate(file1,file2));
    }

    public static String getType() {
        return type;
    }

}
