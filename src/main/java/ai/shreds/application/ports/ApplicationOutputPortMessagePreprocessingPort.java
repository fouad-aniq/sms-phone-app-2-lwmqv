package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;

/**
 * Port interface for preprocessing SMS messages before routing.
 * Prepares validated messages by formatting and enhancing them with necessary metadata.
 */
public interface ApplicationOutputPortMessagePreprocessingPort {
    /**
     * Prepares an SMS message for routing.
     * Formats the message content and adds necessary metadata.
     * Updates the message status after preprocessing.
     *
     * @param message the SMS message to preprocess
     */
    void prepareForRouting(DomainEntitySMSMessage message);
}
