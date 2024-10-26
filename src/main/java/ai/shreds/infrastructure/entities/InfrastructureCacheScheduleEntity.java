package ai.shreds.infrastructure.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfrastructureCacheScheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private Timestamp scheduledTime;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}