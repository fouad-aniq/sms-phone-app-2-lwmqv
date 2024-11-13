package ai.shreds.infrastructure.exceptions;

/**
 * Exception thrown when there is a failure accessing the cache in the infrastructure layer.
 */
public class InfrastructureCacheAccessException extends Exception {

    public String message;

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InfrastructureCacheAccessException with the specified detail message.
     *
     * @param message the detail message describing the cache access failure.
     */
    public InfrastructureCacheAccessException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructs a new InfrastructureCacheAccessException with the specified detail message and cause.
     *
     * @param message the detail message describing the cache access failure.
     * @param cause the cause of the exception.
     */
    public InfrastructureCacheAccessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
