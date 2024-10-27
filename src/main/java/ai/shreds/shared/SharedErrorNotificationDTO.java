package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Error Notification.
 * Represents error notifications received from the SMS Gateway Integration Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedErrorNotificationDTO {
    /**
     * Unique identifier of the dispatched message.
     */
    private String messageId;

    /**
     * Status of the message dispatch ('failed').
     */
    private String dispatchStatus;

    /**
     * Code representing the error type.
     */
    private String errorCode;

    /**
     * Detailed error message explaining the failure.
     */
    private String errorMessage;
}