package ai.shreds.domain.exceptions;

public class DomainInvalidScheduleException extends Exception {

    private final String message;

    public DomainInvalidScheduleException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}