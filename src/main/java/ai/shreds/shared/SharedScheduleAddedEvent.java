package ai.shreds.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the event triggered when a new schedule is added to the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedScheduleAddedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp scheduledTime;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}