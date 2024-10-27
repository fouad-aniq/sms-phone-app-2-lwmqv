package ai.shreds.domain.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntityMessageRequest {
    private String id;
    private String content;
    private String recipient;
    private Date timestamp;
    private String priorityLevel;
    private Object metadata;
}