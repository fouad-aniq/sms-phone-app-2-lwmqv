package ai.shreds.domain.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DomainEntityMessageRequest {
    private String id;
    private String senderId;
    private String recipientNumber;
    private String content;
    private Date requestTimestamp;
    private String status;
}