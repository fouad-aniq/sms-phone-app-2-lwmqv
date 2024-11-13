package ai.shreds.infrastructure.exceptions;

import java.lang.Exception;
import java.lang.Throwable;

/**
 * Exception class to encapsulate data access errors occurring in the ScheduleRepository implementation.
 * This exception is thrown when database access operations fail during schedule persistence, retrieval, or deletion.
 * Error messages provided should be meaningful for debugging but not expose sensitive details.
 */
public class InfrastructureDataAccessException extends Exception {

    /**
     * Constructs a new InfrastructureDataAccessException with the specified detail message.
     * 
     * @param message the detail message providing information about the data access error.
     */
    public InfrastructureDataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new InfrastructureDataAccessException with the specified detail message and cause.
     * 
     * @param message the detail message providing information about the data access error.
     * @param cause the cause of the exception (can be retrieved later using the {@link Throwable#getCause()} method).
     */
    public InfrastructureDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InfrastructureDataAccessException with the specified cause.
     * 
     * @param cause the cause of the exception (can be retrieved later using the {@link Throwable#getCause()} method).
     */
    public InfrastructureDataAccessException(Throwable cause) {
        super(cause);
    }
}