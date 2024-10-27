package ai.shreds.shared;

import java.time.Duration;

public class SharedUtilRetryPolicy {

    private final Integer maxAttempts;
    private final Duration waitInterval;
    private final Double backoffMultiplier;

    public SharedUtilRetryPolicy(Integer maxAttempts, Duration waitInterval, Double backoffMultiplier) {
        this.maxAttempts = maxAttempts;
        this.waitInterval = waitInterval;
        this.backoffMultiplier = backoffMultiplier;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public Duration getWaitInterval() {
        return waitInterval;
    }

    public Double getBackoffMultiplier() {
        return backoffMultiplier;
    }
}