package org.viktsh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Viktor Shvidkiy
 */
public class MainClass {
    public static void main( String[] args )
    {
        File inputFile = new File("files\\input_file.txt");
        Map<String, Integer> words = new HashMap<>();
        try {
            Files.lines(inputFile.toPath())
                    .map(line-> Arrays.stream(line.split(" ")))
                    .flatMap(word->word)
                    .map(word->word.replaceAll("[^А-Яа-я-]", ""))
                    .filter(word -> word.matches("[А-Яа-я-]+"))
                    .map(word -> word.toLowerCase())
                    .forEach(word->words.put(word, words.getOrDefault(word, 0)+1));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File reportAlph= new File("files\\report_by_alph.txt");
        File reportFreq = new File ("files\\report_by_freq.txt");
    }
}
