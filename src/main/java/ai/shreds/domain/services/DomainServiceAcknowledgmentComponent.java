package ai.shreds.domain.services;

import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import java.util.List;

public class DomainServiceAcknowledgmentComponent {

    public SharedResponseDTO generateSuccessResponse(String messageId) {
        SharedResponseDTO response = new SharedResponseDTO();
        response.setStatus(SharedEnumMessageStatusEnum.VALIDATED.name());
        response.setMessage("Message processed successfully.");
        response.setMessageId(messageId);
        response.setErrors(null);
        return response;
    }

    public SharedResponseDTO generateErrorResponse(String messageId, List<SharedValidationErrorDTO> errors) {
        SharedResponseDTO response = new SharedResponseDTO();
        response.setStatus(SharedEnumMessageStatusEnum.FAILED.name());
        response.setMessage("Message processing failed due to validation errors.");
        response.setMessageId(messageId);
        response.setErrors(errors);
        return response;
    }
}
