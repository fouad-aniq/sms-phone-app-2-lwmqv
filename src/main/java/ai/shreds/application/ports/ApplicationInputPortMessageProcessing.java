package ai.shreds.application.ports;

import ai.shreds.shared.SharedMessageRequestDTO;
import ai.shreds.shared.SharedProcessedMessageDTO;

/**
 * ApplicationInputPortMessageProcessing is the interface defining the contract
 * for processing messages within the application layer.
 * It acts as an input port in the hexagonal architecture, allowing external adapters
 * to interact with the application core without being tightly coupled to its implementation.
 */
public interface ApplicationInputPortMessageProcessing {
    /**
     * Processes the given message request and returns the processed message.
     * This method initiates the message processing workflow, including personalization,
     * validation, business rule application, and preparation for dispatch as per the PLAN.
     *
     * @param message the message request to process
     * @return the processed message after all processing steps
     */
    SharedProcessedMessageDTO processMessage(SharedMessageRequestDTO message);
}