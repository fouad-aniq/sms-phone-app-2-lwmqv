package ai.shreds.domain.entities;

import lombok.Data;

import java.util.UUID;
import java.sql.Timestamp;

@Data
public class DomainEntityMessageProcessingEvent {
    private UUID eventId;
    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp triggeredAt;
}