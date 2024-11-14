package ai.shreds.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.sql.Timestamp;
import java.util.Map;
import ai.shreds.shared.SharedEnumMessageStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainEntitySMSMessage {
    private String messageId;
    private String recipientNumber;
    private String content;
    private Map<String, String> metadata;
    private SharedEnumMessageStatusEnum status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}