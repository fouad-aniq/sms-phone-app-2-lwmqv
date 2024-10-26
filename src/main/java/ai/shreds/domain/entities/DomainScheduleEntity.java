package ai.shreds.domain.entities;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainScheduleEntity {

    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp scheduledTime;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}