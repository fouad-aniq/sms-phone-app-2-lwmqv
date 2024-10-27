package ai.shreds.infrastructure.exceptions;

public class InfrastructureExceptionDataAccessException extends Exception {

    public InfrastructureExceptionDataAccessException(String message) {
        super(message);
    }

    public InfrastructureExceptionDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}