package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDTO;

/**
 * This interface defines the contract for triggering message sending processes.
 * It provides a method to trigger messages based on the provided schedule.
 */
public interface ApplicationOutputPortMessageTrigger {

    /**
     * Triggers the message sending process for the given schedule.
     *
     * @param schedule the schedule data transfer object containing message details
     */
    void triggerMessage(SharedScheduleDTO schedule);

}