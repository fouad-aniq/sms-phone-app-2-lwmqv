package ai.shreds.application.exceptions;

public class ApplicationExceptionDispatchMessage extends Exception {

    public ApplicationExceptionDispatchMessage() {
        super();
    }

    public ApplicationExceptionDispatchMessage(String message) {
        super(message);
    }

    public ApplicationExceptionDispatchMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationExceptionDispatchMessage(Throwable cause) {
        super(cause);
    }
}