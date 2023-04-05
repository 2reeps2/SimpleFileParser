import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class TextFileInfo {

    public static void main(String[] args) {

    }


    public static void printFileInfo(String filePath) {
        try {
            File textFile = new File(filePath);
            String fileName = textFile.getName();
            Scanner scanner = new Scanner(textFile);
            int wordCount = 0;
            int dotCount = 0;
            Map<String, Integer> wordFrequencyMap = new HashMap<>();

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.endsWith(".")) {
                    dotCount++;
                }
                wordCount++;
                wordFrequencyMap.merge(word.toLowerCase(), 1, Integer::sum);
            }

            String mostUsedWord = "";
            int maxFrequency = 0;
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                if (entry.getValue() > maxFrequency) {
                    mostUsedWord = entry.getKey();
                    maxFrequency = entry.getValue();
                }
            }

            System.out.println("File Name: " + fileName);
            System.out.println("Word Count: " + wordCount);
            System.out.println("Dot Count: " + dotCount);
            System.out.println("Most Used Word: " + mostUsedWord);
            System.out.println("----------------------");

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
