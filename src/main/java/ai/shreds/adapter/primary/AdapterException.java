package ai.shreds.adapter.primary;

/**
 * AdapterException is a custom exception used in the adapter.primary layer.
 * It represents exceptions that occur within the AdapterMessageController and other classes in this layer.
 */
public class AdapterException extends RuntimeException {
    
    /**
     * Constructs a new AdapterException with the specified detail message.
     * 
     * @param message the detail message
     */
    public AdapterException(String message) {
        super(message);
    }

    /**
     * Constructs a new AdapterException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}