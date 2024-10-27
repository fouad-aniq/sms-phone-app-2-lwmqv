package ai.shreds.shared;

import lombok.Value;

/**
 * Value object representing error details in the system.
 */
@Value
public class SharedErrorDetails {
    /**
     * The error code representing the type of error.
     */
    String errorCode;

    /**
     * The detailed error message.
     */
    String errorMessage;
}