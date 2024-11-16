package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * A data transfer object that captures details of validation errors encountered during the validation of an SMSMessage.
 */
@Getter
@ToString
@AllArgsConstructor
public class SharedValidationErrorDTO {
    /**
     * Standardized error code for the validation error.
     */
    private final String errorCode;

    /**
     * Human-readable error message describing the validation error.
     */
    private final String errorMessage;

    /**
     * Timestamp indicating when the validation error occurred.
     */
    private final Timestamp timestamp;
}
