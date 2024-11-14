package ai.shreds.domain.value_objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DomainValueContentValue {

    private String content;

    private static List<String> prohibitedWords;

    static {
        loadProhibitedWords();
    }

    public DomainValueContentValue(String content) {
        this.content = content;
    }

    public boolean isValid() {
        if (content == null || content.length() > 160) {
            return false;
        }
        String contentLowerCase = content.toLowerCase();
        for (String prohibitedWord : prohibitedWords) {
            if (contentLowerCase.contains(prohibitedWord.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public String getContent() {
        return content;
    }

    private static void loadProhibitedWords() {
        prohibitedWords = new ArrayList<>();
        try (InputStream is = DomainValueContentValue.class.getResourceAsStream("/prohibited_words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                prohibitedWords.add(line.trim());
            }
        } catch (IOException | NullPointerException e) {
            // If the file is not found or an error occurs, use a default list
            prohibitedWords = Arrays.asList("badword1", "badword2", "badword3");
        }
    }

    public static void reloadProhibitedWords() {
        loadProhibitedWords();
    }
}