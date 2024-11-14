package ai.shreds.domain.exceptions;

public class DomainExceptionValidationException extends RuntimeException {

    private String errorCode;

    public DomainExceptionValidationException(String message) {
        super(message);
        this.errorCode = "VALIDATION_ERROR";
    }

    public DomainExceptionValidationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "VALIDATION_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
