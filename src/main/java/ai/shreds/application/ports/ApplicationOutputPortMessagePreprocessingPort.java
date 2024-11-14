package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;

/**
 * Interface for preprocessing SMS messages before routing.
 * This interface declares the method prepareForRouting which is intended to:
 * - Enhance the message with additional metadata required for routing.
 * - Update the message's status to reflect preprocessing.
 * - Format the message content as needed for downstream processing.
 * 
 * The implementation should use the Decorator Pattern to dynamically add responsibilities 
 * to the DomainEntitySMSMessage object, such as augmenting it with additional metadata required for routing.
 */
public interface ApplicationOutputPortMessagePreprocessingPort {
    /**
     * Prepares the given SMS message for routing by enhancing it with additional metadata,
     * updating its status, and formatting its content as needed for downstream processing.
     *
     * @param message The DomainEntitySMSMessage to preprocess.
     */
    void prepareForRouting(DomainEntitySMSMessage message);
}
