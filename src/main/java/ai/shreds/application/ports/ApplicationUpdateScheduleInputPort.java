package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;
import java.util.UUID;

/**
 * Interface for updating an existing schedule.
 * 
 * <p>
 * Implementations should perform the following:
 * </p>
 * <ul>
 *   <li>Validate that the provided 'scheduleId' exists before performing the update.</li>
 *   <li>Validate 'scheduleDto' fields according to business and validation rules, including:</li>
 *   <ul>
 *     <li>If 'scheduledTime' is provided, ensure it is a future timestamp.</li>
 *     <li>If 'status' is provided, confirm it is one of the allowed values: 'active', 'paused', or 'deleted'.</li>
 *     <li>If 'messageContent' is provided, ensure it is not empty.</li>
 *     <li>If 'recipient' is provided, validate it to be in a valid contact format such as a valid email or phone number.</li>
 *   </ul>
 *   <li>Check that the update does not result in a duplicate schedule according to business rules.</li>
 *   <li>Update only the fields provided in 'scheduleDto'; retain existing values for other fields.</li>
 *   <li>Utilize optimistic locking mechanisms to handle high concurrency and ensure thread safety during schedule operations.</li>
 *   <li>Update the schedule in the schedule repository (database).</li>
 *   <li>Ensure data consistency by updating the cache immediately after modifying the schedule in the database.</li>
 *   <li>Handle error cases including validation errors, non-existent 'scheduleId', potential duplicate schedules, and provide uniform error responses.</li>
 *   <li>Return the complete updated schedule details including 'scheduleId', all fields, and timestamps.</li>
 * </ul>
 */
public interface ApplicationUpdateScheduleInputPort {

    /**
     * Updates an existing schedule identified by the given 'scheduleId' with the provided data in 'scheduleDto'.
     *
     * @param scheduleId the unique identifier of the schedule to be updated
     * @param scheduleDto the data transfer object containing the updated schedule information
     * @return the updated schedule as a SharedScheduleDto
     */
    SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto);
}
