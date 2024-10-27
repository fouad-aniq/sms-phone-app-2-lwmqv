package ai.shreds.domain.exceptions;

public class DomainExceptionAcknowledgmentProcessing extends RuntimeException {

    public DomainExceptionAcknowledgmentProcessing(String message) {
        super(message);
    }

    public DomainExceptionAcknowledgmentProcessing(String message, Throwable cause) {
        super(message, cause);
    }
}