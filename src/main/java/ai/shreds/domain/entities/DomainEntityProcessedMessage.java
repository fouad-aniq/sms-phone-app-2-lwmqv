package ai.shreds.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import ai.shreds.shared.SharedEnumDispatchStatus;
import ai.shreds.domain.entities.DomainEntityDeliveryDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntityProcessedMessage {

    private String id;

    private String originalMessageId;

    private String personalizedContent;

    private Boolean validationStatus;

    private List<String> businessRulesApplied;

    private Boolean preparedForDispatch;

    private SharedEnumDispatchStatus dispatchStatus;

    private Date dispatchTimestamp;

    private DomainEntityDeliveryDetails deliveryDetails;
}