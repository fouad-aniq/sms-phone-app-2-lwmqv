package ai.shreds.infrastructure.entities;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Schedule")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InfrastructureCacheScheduleEntity implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private OffsetDateTime scheduledTime;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void setStatus(String status) {
        if (!"active".equals(status) && !"paused".equals(status) && !"deleted".equals(status)) {
            throw new IllegalArgumentException("Invalid status value");
        }
        this.status = status;
    }

}