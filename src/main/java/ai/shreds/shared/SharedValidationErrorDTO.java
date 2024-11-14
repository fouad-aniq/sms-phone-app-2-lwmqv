package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;

/**
 * Data Transfer Object for capturing validation error details encountered during the validation of an SMSMessage.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedValidationErrorDTO {
    /**
     * Error code representing the validation error type, adhering to standardized error codes as per the technical specifications.
     */
    private String errorCode;

    /**
     * Detailed description of the validation error.
     */
    private String errorMessage;

    /**
     * Timestamp indicating when the error occurred.
     */
    private Timestamp timestamp;
}
