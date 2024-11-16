package ai.shreds.application.ports;

import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedResponseDTO;

/**
 * ApplicationInputPortMessageReceptionPort is an interface defining the contract
 * for receiving SMS messages from the Message Processing Service.
 */
public interface ApplicationInputPortMessageReceptionPort {
    /**
     * Receives an SMS message DTO, converts it to domain entity, and initiates validation and preprocessing.
     *
     * @param messageDto the SMS message data transfer object
     * @return a response data transfer object indicating the result of the operation
     */
    SharedResponseDTO receiveMessage(SharedSMSMessageDTO messageDto);
}
