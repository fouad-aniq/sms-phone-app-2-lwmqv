package ai.shreds.domain.services;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class DomainServiceContentFilterComponent {

    private static final int MAX_CONTENT_LENGTH = 160;
    private Set<String> prohibitedWords;

    public DomainServiceContentFilterComponent() {
        // Load prohibited words from configurable data source
        this.prohibitedWords = loadProhibitedWords();
    }

    public boolean checkForProhibitedContent(String content) {
        if (prohibitedWords == null || prohibitedWords.isEmpty()) {
            prohibitedWords = loadProhibitedWords();
        }
        for (String word : prohibitedWords) {
            if (content.contains(word)) {
                return true; // prohibited content found
            }
        }
        return false; // no prohibited content found
    }

    public boolean validateContentLength(String content) {
        return content.length() <= MAX_CONTENT_LENGTH;
    }

    public void reloadProhibitedContentList() {
        // Logic to reload the prohibited words list without restarting the application
        prohibitedWords = loadProhibitedWords();
    }

    private Set<String> loadProhibitedWords() {
        // Load prohibited words from configurable data source (e.g., database, external file)
        // Implement the actual loading mechanism here
        // For demonstration purposes, return a hardcoded set
        return new HashSet<>(Arrays.asList("prohibitedWord1", "prohibitedPhrase1"));
    }
}
