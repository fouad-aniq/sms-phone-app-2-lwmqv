package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedValidationResponse;

public interface ApplicationValidateScheduleInputPort {
    /**
     * Validates the given schedule data without persisting it.
     * <p>
     * The validation should ensure:
     * <ul>
     *     <li>'messageContent' is provided and not empty.</li>
     *     <li>'recipient' is provided and is a valid email address or phone number.</li>
     *     <li>'scheduledTime' is provided, in ISO 8601 format, and is a future timestamp.</li>
     *     <li>'status', if provided, is one of 'active', 'paused', or 'deleted'.</li>
     *     <li>No duplicate schedules exist with the same 'messageContent', 'recipient', and 'scheduledTime'.</li>
     * </ul>
     * 
     * @param scheduleDto the schedule data to validate
     * @return a SharedValidationResponse indicating whether the data is valid and any validation errors
     */
    SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto);
}
