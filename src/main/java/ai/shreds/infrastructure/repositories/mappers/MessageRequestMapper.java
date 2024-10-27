package ai.shreds.domain.entities;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DomainEntityMessageRequest {

    private String id;

    private String content;

    private String recipient;

    private LocalDateTime scheduledTime;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private MessageRequestStatus status = MessageRequestStatus.NEW;

    private String senderId;

    private List<String> attachments;

    public enum MessageRequestStatus {
        NEW,
        PROCESSING,
        PROCESSED,
        FAILED
    }

    // Ensure getters are available for all fields
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MessageRequestStatus getStatus() {
        return status;
    }

    public String getSenderId() {
        return senderId;
    }

    public List<String> getAttachments() {
        return attachments;
    }
}