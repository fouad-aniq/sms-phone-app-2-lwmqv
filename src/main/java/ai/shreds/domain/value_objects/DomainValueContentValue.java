package ai.shreds.domain.value_objects;

import java.util.Arrays;
import java.util.List;

public class DomainValueContentValue {
    private static final int MAX_CONTENT_LENGTH = 160;
    private static final List<String> PROHIBITED_WORDS = Arrays.asList(
            "spam",
            "scam",
            "fraud"
    );

    private String content;

    public DomainValueContentValue(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean isValid() {
        return validateContentLength() && !containsProhibitedWords();
    }

    private boolean validateContentLength() {
        return content != null && content.length() <= MAX_CONTENT_LENGTH;
    }

    private boolean containsProhibitedWords() {
        if (content == null) {
            return false;
        }
        String lowerCaseContent = content.toLowerCase();
        for (String word : PROHIBITED_WORDS) {
            if (lowerCaseContent.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
