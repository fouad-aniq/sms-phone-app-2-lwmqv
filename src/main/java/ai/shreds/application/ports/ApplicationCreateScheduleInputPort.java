package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;

/**
 * Interface defining the contract for creating schedules.
 */
public interface ApplicationCreateScheduleInputPort {

    /**
     * Creates a new schedule with the provided schedule data.
     *
     * Implementations should:
     * <ul>
     *  <li>Validate that all mandatory fields ('messageContent', 'recipient', 'scheduledTime') are provided and valid.</li>
     *  <li>Ensure 'messageContent' is not empty and meets content requirements.</li>
     *  <li>Ensure 'recipient' is in a valid contact format (e.g., valid email or phone number).</li>
     *  <li>Validate that 'scheduledTime' is in ISO 8601 format and is a future timestamp (cannot be in the past).</li>
     *  <li>Validate that 'status' is one of the predefined values: 'active', 'paused', 'deleted'; default to 'active' if not provided.</li>
     *  <li>Prevent the creation of duplicate schedules with the same 'messageContent', 'recipient', and 'scheduledTime'.</li>
     *  <li>Handle validation errors appropriately, possibly by throwing exceptions such as DomainInvalidScheduleException.</li>
     *  <li>Ensure transaction management is in place to maintain data integrity during schedule creation.</li>
     *  <li>Upon successful validation, persist the new schedule to the PostgreSQL database.</li>
     *  <li>Update the Redis cache with the new schedule after it has been saved to the database.</li>
     *  <li>Return the created schedule data, including 'scheduleId', 'messageContent', 'recipient', 'scheduledTime', 'status', 'createdAt', and 'updatedAt'.</li>
     * </ul>
     *
     * @param scheduleDto the schedule data to create
     * @return the created schedule data including all relevant fields
     */
    SharedScheduleDto createSchedule(SharedScheduleDto scheduleDto);

}