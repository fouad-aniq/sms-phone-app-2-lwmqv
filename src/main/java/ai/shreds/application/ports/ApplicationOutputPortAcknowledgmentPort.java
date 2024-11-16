package ai.shreds.application.ports;

import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import java.util.List;

public interface ApplicationOutputPortAcknowledgmentPort {
    
    /**
     * Creates a success acknowledgment response for a successfully validated message.
     * 
     * @param messageId the ID of the message that was successfully processed
     * @return a SharedResponseDTO indicating success
     */
    SharedResponseDTO createSuccessResponse(String messageId);

    /**
     * Creates an error acknowledgment response containing validation error details.
     * 
     * @param messageId the ID of the message that failed validation
     * @param errors a list of validation errors encountered during processing
     * @return a SharedResponseDTO with error details
     */
    SharedResponseDTO createErrorResponse(String messageId, List<SharedValidationErrorDTO> errors);
}
