package ai.shreds.application.ports;

import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedResponseDTO;

public interface ApplicationInputPortMessageReceptionPort {
    SharedResponseDTO receiveMessage(SharedSMSMessageDTO messageDto);
}