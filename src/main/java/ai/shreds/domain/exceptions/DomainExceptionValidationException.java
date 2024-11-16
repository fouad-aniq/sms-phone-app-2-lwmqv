package ai.shreds.domain.exceptions;

/**
 * Exception thrown when validation errors occur within the domain layer.
 * This exception adheres to the standardized error reporting mechanism.
 */
public class DomainExceptionValidationException extends Exception {

    /**
     * Constructs a new DomainExceptionValidationException with the specified detail message.
     * @param message the detail message.
     */
    public DomainExceptionValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new DomainExceptionValidationException with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public DomainExceptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
