package ai.shreds.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "schedules", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"message_content", "recipient", "scheduled_time"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfrastructureScheduleEntity {

    @Id
    @Column(name = "schedule_id", nullable = false, updatable = false)
    private UUID scheduleId;

    @NotEmpty
    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @NotEmpty
    @Column(name = "recipient", nullable = false)
    private String recipient;

    @NotNull
    @Column(name = "scheduled_time", nullable = false)
    private Timestamp scheduledTime;

    @NotEmpty
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}