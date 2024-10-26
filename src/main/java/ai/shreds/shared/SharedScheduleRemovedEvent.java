package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event representing the removal of a schedule.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedScheduleRemovedEvent {
    private UUID scheduleId;
}