package ai.shreds.shared;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedDeliveryConfirmationResponse {
    private String status;
    private String messageId;
}