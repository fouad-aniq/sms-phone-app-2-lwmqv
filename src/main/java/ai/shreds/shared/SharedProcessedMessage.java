package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents a processed message after undergoing personalization, validation,
 * and the application of business rules in the message processing workflow.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedProcessedMessage implements Serializable {
    /**
     * Unique identifier for the processed message.
     */
    private String id;

    /**
     * Reference to the original MessageRequest ID.
     */
    private String originalMessageId;

    /**
     * The content after personalization has been applied.
     */
    private String personalizedContent;

    /**
     * Indicates whether the message passed validation against policies.
     */
    private Boolean validationStatus;

    /**
     * List of business rules that were applied during processing.
     */
    private List<String> businessRulesApplied;

    /**
     * Indicates if the message is ready to be dispatched.
     */
    private Boolean preparedForDispatch;

    /**
     * The time when the message was processed.
     */
    private Date timestamp;
}