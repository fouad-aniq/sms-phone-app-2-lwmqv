package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationOutputPortAcknowledgmentPort;
import ai.shreds.domain.services.DomainServiceAcknowledgmentComponent;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceAcknowledgmentService implements ApplicationOutputPortAcknowledgmentPort {

    private final DomainServiceAcknowledgmentComponent domainServiceAcknowledgmentComponent;

    @Autowired
    public ApplicationServiceAcknowledgmentService(DomainServiceAcknowledgmentComponent domainServiceAcknowledgmentComponent) {
        this.domainServiceAcknowledgmentComponent = domainServiceAcknowledgmentComponent;
    }

    @Override
    public SharedResponseDTO createSuccessResponse(String messageId) {
        try {
            return domainServiceAcknowledgmentComponent.generateSuccessResponse(messageId);
        } catch (Exception e) {
            return generateInternalServerErrorResponse(messageId);
        }
    }

    @Override
    public SharedResponseDTO createErrorResponse(String messageId, List<SharedValidationErrorDTO> errors) {
        try {
            return domainServiceAcknowledgmentComponent.generateErrorResponse(messageId, errors);
        } catch (Exception e) {
            return generateInternalServerErrorResponse(messageId);
        }
    }

    private SharedResponseDTO generateInternalServerErrorResponse(String messageId) {
        SharedResponseDTO response = new SharedResponseDTO();
        response.setStatus("ERROR");
        response.setMessage("Internal Server Error");
        response.setMessageId(messageId);
        response.setErrors(null);
        return response;
    }
}
