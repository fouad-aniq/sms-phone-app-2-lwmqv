package ai.shreds.infrastructure.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

/**
 * Entity class for storing delivery details in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDetailsEntity {
    private String deliveryStatus;
    private Date deliveryTimestamp;
    private String providerResponse;
    private String errorCode;
    private String errorMessage;
}