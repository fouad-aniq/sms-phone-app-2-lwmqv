package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedMessageRequestDTO implements Serializable {
    private String id;
    private String senderId;
    private String recipientNumber;
    private String content;
    private Date requestTimestamp;
    private String status;
}