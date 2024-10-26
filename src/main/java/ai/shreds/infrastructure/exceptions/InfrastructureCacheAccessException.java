package ai.shreds.infrastructure.exceptions;

public class InfrastructureCacheAccessException extends Exception {

    public InfrastructureCacheAccessException(String message) {
        super(message);
    }

    public InfrastructureCacheAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}