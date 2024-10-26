package ai.shreds.domain.exceptions;

public class DomainExceptionScheduleNotFound extends RuntimeException {

    public DomainExceptionScheduleNotFound(String message) {
        super(message);
    }

    public DomainExceptionScheduleNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}