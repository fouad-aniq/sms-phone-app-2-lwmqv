package ai.shreds.application.mappers;

import ai.shreds.shared.SharedMessageRequestDTO;
import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;

public class MessageMapper {

    public DomainEntityMessageRequest toDomain(SharedMessageRequestDTO message) {
        if (message == null) {
            return null;
        }
        return DomainEntityMessageRequest.builder()
                .id(message.getId())
                .content(message.getContent())
                .recipient(message.getRecipient())
                .timestamp(message.getTimestamp())
                .priorityLevel(message.getPriorityLevel())
                .metadata(message.getMetadata())
                .build();
    }

    public SharedProcessedMessageDTO toDTO(DomainEntityProcessedMessage message) {
        if (message == null) {
            return null;
        }
        return SharedProcessedMessageDTO.builder()
                .id(message.getId())
                .originalMessageId(message.getOriginalMessageId())
                .personalizedContent(message.getPersonalizedContent())
                .validationStatus(message.getValidationStatus() != null && message.getValidationStatus())
                .businessRulesApplied(message.getBusinessRulesApplied())
                .preparedForDispatch(message.getPreparedForDispatch() != null && message.getPreparedForDispatch())
                .timestamp(message.getTimestamp().toInstant())
                .build();
    }
}