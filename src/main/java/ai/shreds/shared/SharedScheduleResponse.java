package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

/**
 * Data Transfer Object representing the response returned upon successful deletion of a schedule.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedScheduleResponse {

    /**
     * Confirmation message indicating successful deletion.
     */
    private String message;

    /**
     * The UUID of the deleted schedule.
     */
    private UUID scheduleId;

}