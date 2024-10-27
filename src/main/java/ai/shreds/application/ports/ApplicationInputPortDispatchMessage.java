package ai.shreds.application.ports;

import ai.shreds.shared.SharedProcessedMessageDTO;

public interface ApplicationInputPortDispatchMessage {
    void dispatchMessage(SharedProcessedMessageDTO messageDTO);
}