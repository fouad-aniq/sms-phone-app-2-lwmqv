package ai.shreds.infrastructure.utils;

import lombok.Value;
import java.time.Duration;

/**
 * Utility class representing the retry policy configuration for the application.
 */
@Value
public class SharedUtilRetryPolicy {

    /**
     * The maximum number of retry attempts.
     */
    private final Integer maxAttempts;

    /**
     * The initial wait interval between retries.
     */
    private final Duration waitInterval;

    /**
     * The backoff multiplier for exponential backoff strategy.
     */
    private final Double backoffMultiplier;
}