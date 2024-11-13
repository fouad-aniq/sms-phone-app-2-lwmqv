package ai.shreds.application.ports;

import java.util.UUID;
import ai.shreds.shared.SharedScheduleResponse;

/**
 * Interface for deleting schedules in the application layer.
 */
public interface ApplicationDeleteScheduleInputPort {

    /**
     * Deletes the schedule identified by the given scheduleId.
     * <p>
     * Business Rules:
     * <ul>
     *   <li>Verify that a schedule with the provided scheduleId exists before attempting deletion.</li>
     *   <li>If the schedule does not exist, handle as per error specifications (e.g., throw an exception or return an error response indicating that the schedule was not found).</li>
     *   <li>Upon successful deletion, remove the schedule from both the database and the cache to maintain data consistency.</li>
     *   <li>Ensure data consistency between the database and cache after deletion operations.</li>
     *   <li>Return a confirmation message and the ID of the deleted schedule upon successful deletion.</li>
     * </ul>
     * </p>
     *
     * @param scheduleId the unique identifier (UUID) of the schedule to be deleted
     * @return SharedScheduleResponse containing a confirmation message and the ID of the deleted schedule
     */
    SharedScheduleResponse deleteSchedule(UUID scheduleId);
}
