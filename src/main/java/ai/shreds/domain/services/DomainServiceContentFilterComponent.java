package ai.shreds.domain.services;

import java.util.List;
import java.util.Arrays;

public class DomainServiceContentFilterComponent {
    private static final int MAX_CONTENT_LENGTH = 160;
    private List<String> prohibitedContentList;

    public DomainServiceContentFilterComponent() {
        // Load prohibited content list from a configurable data source
        // For demonstration, we initialize it with some sample prohibited words
        this.prohibitedContentList = loadProhibitedContentList();
    }

    public DomainServiceContentFilterComponent(List<String> prohibitedContentList) {
        this.prohibitedContentList = prohibitedContentList;
    }

    private List<String> loadProhibitedContentList() {
        // In a real-world scenario, this method would load the list from a database or external file
        return Arrays.asList("prohibitedWord1", "prohibitedPhrase1");
    }

    public boolean checkForProhibitedContent(String content) {
        for (String prohibitedWord : prohibitedContentList) {
            if (content.toLowerCase().contains(prohibitedWord.toLowerCase())) {
                return true; // Prohibited content found
            }
        }
        return false; // No prohibited content found
    }

    public boolean validateContentLength(String content) {
        return content.length() <= MAX_CONTENT_LENGTH;
    }

    public void updateProhibitedContentList(List<String> newProhibitedContentList) {
        this.prohibitedContentList = newProhibitedContentList;
    }
}
