package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedProcessedMessageDTO {
    private String id;
    private String originalMessageId;
    private String personalizedContent;
    private Boolean validationStatus;
    private List<String> businessRulesApplied;
    private Boolean preparedForDispatch;
    private SharedEnumDispatchStatus dispatchStatus;
    private Date dispatchTimestamp;
    private SharedDeliveryDetailsDTO deliveryDetails;
}