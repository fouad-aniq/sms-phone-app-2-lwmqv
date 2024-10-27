package ai.shreds.domain.entities;

import lombok.Data;
import lombok.Builder;
import java.util.Date;

@Data
@Builder
public class DomainEntityDeliveryDetails {
    private String deliveryStatus;
    private Date deliveryTimestamp;
    private String providerResponse;
    private String errorCode;
    private String errorMessage;

    // Added method to retrieve recipient number
    public String getRecipientNumber() {
        // Assuming recipient number is part of provider response or needs to be retrieved differently
        // Placeholder implementation, should be replaced with actual logic
        return "RecipientNumberPlaceholder";
    }
}