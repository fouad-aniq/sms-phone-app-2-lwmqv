package ai.shreds.domain.entities;

import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedUtilDateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DomainEntitySMSMessage {

    private String messageId;
    private String recipientNumber;
    private String content;
    private Map<String, String> metadata;
    private SharedEnumMessageStatusEnum status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<DomainEntityValidationError> validationErrors;

    public static DomainEntitySMSMessage fromSharedDTO(SharedSMSMessageDTO dto) {
        DomainEntitySMSMessage message = new DomainEntitySMSMessage();
        message.setMessageId(dto.getMessageId());
        message.setRecipientNumber(dto.getRecipientNumber());
        message.setContent(dto.getContent());
        message.setMetadata(dto.getMetadata());
        message.setStatus(SharedEnumMessageStatusEnum.RECEIVED);
        Timestamp currentTimestamp = Timestamp.valueOf(SharedUtilDateUtil.getCurrentTimestamp());
        message.setCreatedAt(currentTimestamp);
        message.setUpdatedAt(currentTimestamp);
        return message;
    }

    public SharedSMSMessageDTO toSharedDTO() {
        SharedSMSMessageDTO dto = new SharedSMSMessageDTO();
        dto.setMessageId(this.messageId);
        dto.setRecipientNumber(this.recipientNumber);
        dto.setContent(this.content);
        dto.setMetadata(this.metadata);
        return dto;
    }

    public void updateTimestamps() {
        this.updatedAt = Timestamp.valueOf(SharedUtilDateUtil.getCurrentTimestamp());
    }

    public void setStatusToValidated() {
        this.status = SharedEnumMessageStatusEnum.VALIDATED;
        updateTimestamps();
    }

    public void setStatusToFailed() {
        this.status = SharedEnumMessageStatusEnum.FAILED;
        updateTimestamps();
    }

    public void addValidationError(DomainEntityValidationError error) {
        this.validationErrors.add(error);
    }
}