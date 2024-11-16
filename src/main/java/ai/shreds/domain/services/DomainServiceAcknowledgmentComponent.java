package ai.shreds.domain.services;

import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import java.util.List;
import java.util.stream.Collectors;

public class DomainServiceAcknowledgmentComponent {

    public SharedResponseDTO generateSuccessResponse(String messageId) {
        return new SharedResponseDTO(
                "SUCCESS",
                "Message processed successfully.",
                messageId,
                null
        );
    }

    public SharedResponseDTO generateErrorResponse(String messageId, List<SharedValidationErrorDTO> errors) {
        List<String> errorMessages = extractErrorMessages(errors);

        return new SharedResponseDTO(
                "ERROR",
                "Message processing failed due to validation errors.",
                messageId,
                errorMessages
        );
    }

    private List<String> extractErrorMessages(List<SharedValidationErrorDTO> errors) {
        return errors.stream()
                .map(error -> String.format("%s: %s", error.getErrorCode(), error.getErrorMessage()))
                .collect(Collectors.toList());
    }
}
