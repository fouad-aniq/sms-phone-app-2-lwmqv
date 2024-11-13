package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedValidationResponse;

/**
 * This interface defines the contract for validating schedule data within the application layer.
 * It specifies a method to validate the schedule data encapsulated in a SharedScheduleDto object
 * and returns a SharedValidationResponse indicating the validation results.
 */
public interface ApplicationValidateScheduleInputPort {
    /**
     * Validates the provided schedule data.
     *
     * @param scheduleDto the schedule data to be validated
     * @return a SharedValidationResponse containing the validation results
     */
    SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto);
}