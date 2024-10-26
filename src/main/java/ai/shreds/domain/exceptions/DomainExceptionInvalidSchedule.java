package ai.shreds.domain.exceptions;

public class DomainExceptionInvalidSchedule extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DomainExceptionInvalidSchedule(String message) {
        super(message);
    }

}