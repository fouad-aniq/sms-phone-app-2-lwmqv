package ai.shreds.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class SharedUtilDateUtil {

    private SharedUtilDateUtil() {
        // Private constructor to prevent instantiation
    }

    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(formatter);
    }
}
