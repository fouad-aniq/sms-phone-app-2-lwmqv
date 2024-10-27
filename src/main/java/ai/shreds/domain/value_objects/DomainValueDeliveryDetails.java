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

    // Constructor matching parameters used in ApplicationServiceMessageDispatchService
    public DomainValueDeliveryDetails(String providerResponseCode, String deliveryReceiptDetails, String failureReason) {
        this.providerResponseCode = providerResponseCode;
        this.deliveryReceiptDetails = deliveryReceiptDetails;
        this.failureReason = failureReason;
    }

    // Getter methods
    public String getProviderResponseCode() {
        return providerResponseCode;
    }

    public String getDeliveryReceiptDetails() {
        return deliveryReceiptDetails;
    }

    public String getFailureReason() {
        return failureReason;
    }
}