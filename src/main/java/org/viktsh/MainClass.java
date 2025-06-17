package org.viktsh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Viktor Shvidkiy
 */
public class MainClass {
    public static void main( String[] args )
    {
        Path inputFile = Paths.get("files", "input_file_english.txt");
        Path reportAlph= Paths.get("files","report_by_alph.txt");
        Path reportFreq = Paths.get("files","report_by_freq.txt");
        Map<String, Integer> words = new HashMap<>();
        AtomicInteger totalWordCount = new AtomicInteger();
        try {
            Files.lines(inputFile)
                    .map(line-> Arrays.stream(line.split("\\s+")))
                    .flatMap(word->word)
                    .map(word->word.replaceAll("[^А-Яа-яёЁ-]", ""))
                    .filter(word -> word.matches("[А-Яа-яёЁ]+(-[А-Яа-яёЁ]+)*"))
                    .map(String::toLowerCase)
                    .forEach(word->{
                        words.put(word, words.getOrDefault(word, 0)+1);
                        totalWordCount.incrementAndGet();
                    });
            if(totalWordCount.get()==0) throw new CustomWordsException("Не найдено русских слов");

        } catch (IOException e) {
            throw new CustomWordsException("Ошибка чтения файла",e);
        }

        try (PrintWriter pw = new PrintWriter(reportAlph.toFile())){
            pw.println("слово                | частота | относительная частота");
            pw.println("---------------------+---------+----------------------");
            words.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry->pw.printf("%-20s | %-7s | %-8.6f%%\n",entry.getKey(), entry.getValue()+" раз", (double)entry.getValue()*100/totalWordCount.get()));
        } catch (FileNotFoundException e) {
            throw new CustomWordsException("Ошибка записи файла report_by_alph",e);
        }

        try (PrintWriter pw = new PrintWriter(reportFreq.toFile())){
            pw.println("слово                | частота | относительная частота");
            pw.println("---------------------+---------+----------------------");
            words.entrySet().stream()
                    .sorted((entry1, entry2)->{
                        int compareByValue = entry2.getValue().compareTo(entry1.getValue());
                        return compareByValue !=0 ? compareByValue : entry1.getKey().compareTo(entry2.getKey());
                    })
                    .forEach(entry->pw.printf("%-20s | %-7s | %-8.6f%%\n",entry.getKey(), entry.getValue()+" раз", (double)entry.getValue()*100/totalWordCount.get()));
        } catch (FileNotFoundException e) {
            throw new CustomWordsException("Ошибка записи файла report_by_freq", e);
        }
    }
}
