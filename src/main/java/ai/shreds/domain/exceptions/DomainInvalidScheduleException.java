package ai.shreds.domain.exceptions;

/**
 * DomainInvalidScheduleException is thrown when a Schedule entity fails to pass business validations.
 */
public class DomainInvalidScheduleException extends Exception {
    /**
     * Constructs a new DomainInvalidScheduleException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public DomainInvalidScheduleException(String message) {
        super(message);
    }
}