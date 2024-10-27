package ai.shreds.infrastructure.repositories.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "message_requests")
public class MessageRequestEntity {

    @Id
    private String id;
    private String senderId;
    private String recipientNumber;
    private String content;
    private Date requestTimestamp;
    private String status;
}