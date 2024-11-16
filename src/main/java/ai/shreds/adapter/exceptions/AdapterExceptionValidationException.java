package ai.shreds.adapter.exceptions;

import ai.shreds.adapter.primary.AdapterException;

/**
 * Exception thrown when input validation fails in the adapter layer.
 */
public class AdapterExceptionValidationException extends AdapterException {

    /**
     * Constructs a new AdapterExceptionValidationException with the specified detail message.
     *
     * @param message the detail message.
     */
    public AdapterExceptionValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new AdapterExceptionValidationException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     */
    public AdapterExceptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
