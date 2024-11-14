package ai.shreds.application.ports;

import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import java.util.List;

/**
 * Interface for generating acknowledgment responses to be sent back to the Message Processing Service.
 */
public interface ApplicationOutputPortAcknowledgmentPort {

    /**
     * Creates a success acknowledgment response for a successfully validated message.
     *
     * @param messageId The ID of the message that was successfully processed.
     * @return A SharedResponseDTO containing status, message, and messageId.
     */
    SharedResponseDTO createSuccessResponse(String messageId);

    /**
     * Creates an error acknowledgment response containing validation error details.
     *
     * @param messageId The ID of the message that failed processing.
     * @param errors    A list of validation errors associated with the message.
     * @return A SharedResponseDTO containing status, message, messageId, and list of errors.
     */
    SharedResponseDTO createErrorResponse(String messageId, List<SharedValidationErrorDTO> errors);
}