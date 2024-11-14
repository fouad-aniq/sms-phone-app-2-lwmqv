package ai.shreds.infrastructure.exceptions;

public class InfrastructureExceptionDataAccessException extends RuntimeException {

    public InfrastructureExceptionDataAccessException(String message) {
        super(message);
    }

    public InfrastructureExceptionDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
