package ai.shreds.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ai.shreds.domain.exceptions.DomainExceptionInvalidSchedule;
import ai.shreds.domain.exceptions.DomainExceptionScheduleNotFound;

public class ApplicationServiceErrorHandling {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceErrorHandling.class);

    public void handleError(Exception exception, Object context) {
        // Log the error
        logError(exception);

        // Determine if the operation should be retried
        if (shouldRetry(exception)) {
            // Throw a custom RetryableException
            throw new RetryableException("Retryable exception occurred", exception);
        } else {
            // Handle non-recoverable error
            handleNonRecoverableError(exception, context);
        }
    }

    public void handleNonRecoverableError(Exception exception, Object context) {
        // Log the non-recoverable error with context information
        logger.error("Non-recoverable error occurred: {}. Context: {}", exception.getMessage(), context);
        // Additional logic for handling non-recoverable errors can be added here
    }

    public void logError(Exception exception) {
        // Log the exception details
        logger.error("An error occurred: {}", exception.getMessage(), exception);
    }

    public boolean shouldRetry(Exception exception) {
        // Determine if the exception is transient and the operation should be retried
        if (exception instanceof java.io.IOException || exception instanceof java.net.SocketTimeoutException) {
            // IOExceptions and Network timeouts are considered retryable
            return true;
        } else {
            // Other exceptions are not retryable
            return false;
        }
    }

    // Custom exceptions defined within the same class
    public static class RetryableException extends RuntimeException {
        public RetryableException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NonRetryableException extends RuntimeException {
        public NonRetryableException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}