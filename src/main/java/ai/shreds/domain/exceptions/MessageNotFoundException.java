package ai.shreds.domain.exceptions;

public class MessageNotFoundException extends RuntimeException {

    /**
     * Constructs a new MessageNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public MessageNotFoundException(String message) {
        super(message);
    }
}