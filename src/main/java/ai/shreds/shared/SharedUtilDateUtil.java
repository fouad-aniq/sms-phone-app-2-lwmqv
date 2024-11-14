package ai.shreds.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class SharedUtilDateUtil {

    private SharedUtilDateUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}