package ai.shreds.domain.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

public class DomainScheduleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID scheduleId;
    private String messageContent;
    private String recipient;
    private OffsetDateTime scheduledTime;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public DomainScheduleEntity() {
    }

    public DomainScheduleEntity(UUID scheduleId, String messageContent, String recipient, OffsetDateTime scheduledTime, String status, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.messageContent = messageContent;
        this.recipient = recipient;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public OffsetDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(OffsetDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainScheduleEntity that = (DomainScheduleEntity) o;

        return scheduleId != null ? scheduleId.equals(that.scheduleId) : that.scheduleId == null;
    }

    @Override
    public int hashCode() {
        return scheduleId != null ? scheduleId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DomainScheduleEntity{" +
                "scheduleId=" + scheduleId +
                ", messageContent='" + messageContent + '\'' +
                ", recipient='" + recipient + '\'' +
                ", scheduledTime=" + scheduledTime +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
