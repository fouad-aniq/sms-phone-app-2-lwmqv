package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationOutputPortAcknowledgmentPort;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceAcknowledgmentService implements ApplicationOutputPortAcknowledgmentPort {

    private static final String STATUS_SUCCESS = "200 OK";
    private static final String STATUS_BAD_REQUEST = "400 Bad Request";
    private static final String STATUS_INTERNAL_SERVER_ERROR = "500 Internal Server Error";

    private static final String MESSAGE_SUCCESS = "Message processed successfully.";
    private static final String MESSAGE_VALIDATION_FAILED = "Validation errors occurred.";
    private static final String MESSAGE_INTERNAL_ERROR = "An unexpected error occurred.";

    @Override
    public SharedResponseDTO createSuccessResponse(String messageId) {
        SharedResponseDTO response = new SharedResponseDTO();
        response.setStatus(STATUS_SUCCESS);
        response.setMessage(MESSAGE_SUCCESS);
        response.setMessageId(messageId);
        response.setErrors(null);
        return response;
    }

    @Override
    public SharedResponseDTO createErrorResponse(String messageId, List<SharedValidationErrorDTO> errors) {
        SharedResponseDTO response = new SharedResponseDTO();
        response.setMessageId(messageId);
        if (errors != null && !errors.isEmpty()) {
            response.setStatus(STATUS_BAD_REQUEST);
            response.setMessage(MESSAGE_VALIDATION_FAILED);
            // Extract error messages from SharedValidationErrorDTO list
            List<String> errorMessages = errors.stream()
                    .map(SharedValidationErrorDTO::getErrorMessage)
                    .collect(Collectors.toList());
            response.setErrors(errorMessages);
        } else {
            response.setStatus(STATUS_INTERNAL_SERVER_ERROR);
            response.setMessage(MESSAGE_INTERNAL_ERROR);
            response.setErrors(null);
        }
        return response;
    }
}
