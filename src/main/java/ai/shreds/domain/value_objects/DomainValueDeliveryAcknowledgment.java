package ai.shreds.domain.value_objects;

import lombok.Value;
import java.util.Date;

@Value
public class DomainValueDeliveryAcknowledgment {
    String messageId;
    String deliveryStatus;
    Date deliveryTimestamp;
    String details;
}