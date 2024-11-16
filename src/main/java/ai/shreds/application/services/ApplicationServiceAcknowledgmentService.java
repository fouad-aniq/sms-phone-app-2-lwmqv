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
        return SharedResponseDTO.success(messageId, MESSAGE_SUCCESS);
    }

    @Override
    public SharedResponseDTO createErrorResponse(String messageId, List<SharedValidationErrorDTO> errors) {
        if (errors != null && !errors.isEmpty()) {
            List<String> errorMessages = errors.stream()
                    .map(SharedValidationErrorDTO::getErrorMessage)
                    .collect(Collectors.toList());
            return SharedResponseDTO.error(messageId, MESSAGE_VALIDATION_FAILED, errorMessages);
        } else {
            return SharedResponseDTO.error(messageId, MESSAGE_INTERNAL_ERROR, null);
        }
    }
}