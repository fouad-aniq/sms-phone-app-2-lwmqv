package ai.shreds.domain.entities;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;
import java.sql.Timestamp;
import ai.shreds.shared.SharedValueScheduleStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp scheduledTime;
    private SharedValueScheduleStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}