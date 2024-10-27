package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class MessageRequestMapper {

    public MessageRequestDocument toDocument(DomainEntityMessageRequest messageRequest) {
        if (messageRequest == null) {
            return null;
        }
        MessageRequestDocument document = new MessageRequestDocument();
        document.setId(messageRequest.getId());
        document.setContent(messageRequest.getContent());
        document.setRecipient(messageRequest.getRecipient());
        document.setTimestamp(messageRequest.getTimestamp());
        document.setPriorityLevel(messageRequest.getPriorityLevel());
        document.setMetadata(messageRequest.getMetadata());
        return document;
    }

    public DomainEntityMessageRequest toDomainEntity(MessageRequestDocument document) {
        if (document == null) {
            return null;
        }
        return DomainEntityMessageRequest.builder()
                .id(document.getId())
                .content(document.getContent())
                .recipient(document.getRecipient())
                .timestamp(document.getTimestamp())
                .priorityLevel(document.getPriorityLevel())
                .metadata(document.getMetadata())
                .build();
    }
}