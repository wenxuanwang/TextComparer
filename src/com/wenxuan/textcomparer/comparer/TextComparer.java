package com.wenxuan.textcomparer.comparer;

import com.wenxuan.textcomparer.Main;
import com.wenxuan.textcomparer.algorithm.CosineSimilarityAlgorithm;
import com.wenxuan.textcomparer.preprocess.PreprocessUtils;
import com.wenxuan.textcomparer.preprocess.Preprocessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Wenxuan Wang
 * @version 1.0
 *
 */
public class TextComparer {
    /**
     * Preprocess file contents and pass into the handler
     * @param s1 filename1
     * @param s2 filename2
     * @return cosine similarity of the two files
     */
    public static double calculate(String s1, String s2) {
        String file1Content = "";
        String file2Content = "";
        try{
            file1Content = PreprocessUtils.readFile(s1, StandardCharsets.UTF_8);
            file2Content = PreprocessUtils.readFile(s2, StandardCharsets.UTF_8);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        finally{
            System.out.println("read success");
        }

        file1Content = file1Content.trim();
        file2Content = file2Content.trim();

        if(file1Content.length() == 0 || file2Content.length() == 0)
            return 0;

        if(Main.getType().equals("-c")) {
            file1Content = Preprocessor.removeComment(Preprocessor.removeBlocks(file1Content));
            file2Content = Preprocessor.removeComment(Preprocessor.removeBlocks(file2Content));
        }
        return new CosineSimilarityAlgorithm().match(file1Content, file2Content);
    }
}
