package ai.shreds.domain.value_objects;

import lombok.Value;
import java.io.Serializable;

/**
 * DomainValueDeliveryDetails is a value object that holds detailed information
 * about the delivery of a processed message, including response codes,
 * delivery receipts, and failure reasons.
 */
@Value
public class DomainValueDeliveryDetails implements Serializable {
    private final String providerResponseCode;
    private final String deliveryReceiptDetails;
    private final String failureReason;
}