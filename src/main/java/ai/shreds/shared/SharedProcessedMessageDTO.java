package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Data Transfer Object for Processed Messages.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedProcessedMessageDTO {
    /**
     * Unique identifier for the processed message.
     */
    private String id;

    /**
     * Reference to the original MessageRequest ID.
     */
    private String originalMessageId;

    /**
     * The content after personalization.
     */
    private String personalizedContent;

    /**
     * Indicates if the message passed validation.
     */
    private boolean validationStatus;

    /**
     * List of business rules that were applied.
     */
    private List<String> businessRulesApplied;

    /**
     * Indicates if the message is ready to be dispatched.
     */
    private boolean preparedForDispatch;

    /**
     * The time when the message was processed.
     */
    private Instant timestamp;
}