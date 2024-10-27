package application.ports;

import shared.SharedProcessedMessageDTO;

public interface ApplicationInputPortDispatchMessage {
    void dispatchMessage(SharedProcessedMessageDTO messageDTO);
}