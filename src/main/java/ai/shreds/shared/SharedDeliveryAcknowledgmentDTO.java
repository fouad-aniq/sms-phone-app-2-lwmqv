package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import ai.shreds.shared.SharedEnumDispatchStatus;

/**
 * Data Transfer Object representing a delivery acknowledgment received from the SMS Gateway Integration Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedDeliveryAcknowledgmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier of the dispatched message.
     */
    private String messageId;

    /**
     * Current status of the message dispatch (e.g., 'delivered').
     */
    private SharedEnumDispatchStatus dispatchStatus;

    /**
     * Timestamp when the message was delivered.
     */
    private Date deliveryTimestamp;

    /**
     * Additional details about the delivery.
     */
    private String details;
}