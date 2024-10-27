package ai.shreds.application.ports;

import ai.shreds.shared.SharedAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;

@FunctionalInterface
public interface ApplicationHandleAcknowledgmentInputPort {

    void processAcknowledgment(SharedAcknowledgmentDTO acknowledgment);

    void processErrorNotification(SharedErrorNotificationDTO notification);
}