package ai.shreds.domain.entities;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import ai.shreds.domain.entities.DomainEntityDeliveryDetails;

@Data
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

    private DomainValueDispatchStatus dispatchStatus;

    private Date dispatchTimestamp;

    private DomainEntityDeliveryDetails deliveryDetails;
}