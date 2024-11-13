package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;

/**
 * Defines the contract for validating schedule entities within the domain layer.
 */
public interface DomainScheduleValidationServicePort {

    /**
     * Validates the given schedule entity based on business rules specified in THE PLAN:
     * <ul>
     *     <li>Ensure 'scheduledTime' is a future timestamp; it must not be set for a past time.</li>
     *     <li>Validate that 'status' is one of the allowed values: 'active', 'paused', or 'deleted'.</li>
     *     <li>Check that all mandatory fields ('messageContent', 'recipient', 'scheduledTime') are present and not empty.</li>
     *     <li>Validate that 'recipient' is in a valid contact format.</li>
     *     <li>Detect and prevent duplicate schedules with the same 'messageContent', 'recipient', and 'scheduledTime'.</li>
     * </ul>
     *
     * @param schedule the schedule entity to validate
     * @throws DomainInvalidScheduleException if validation fails with detailed error messages
     */
    void validateSchedule(DomainScheduleEntity schedule) throws DomainInvalidScheduleException;
}
