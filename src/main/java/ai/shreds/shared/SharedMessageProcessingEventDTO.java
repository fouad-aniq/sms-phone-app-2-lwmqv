package ai.shreds.shared;

import java.util.UUID;
import java.sql.Timestamp;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * SharedMessageProcessingEventDTO is a Data Transfer Object representing the event triggered when a message is to be processed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedMessageProcessingEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID eventId;
    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp triggeredAt;
}