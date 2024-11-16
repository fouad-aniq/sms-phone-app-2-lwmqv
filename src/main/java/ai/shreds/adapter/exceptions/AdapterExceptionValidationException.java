package ai.shreds.adapter.exceptions;

public class AdapterExceptionValidationException extends AdapterException {

    public AdapterExceptionValidationException(String message) {
        super(message);
    }

    public AdapterExceptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}