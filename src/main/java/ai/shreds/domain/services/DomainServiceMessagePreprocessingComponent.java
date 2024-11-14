package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.exceptions.DomainExceptionValidationException;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedUtilDateUtil;
import java.sql.Timestamp;
import java.util.Map;

public class DomainServiceMessagePreprocessingComponent {

    private final DomainServiceContentFilterComponent contentFilterComponent;

    public DomainServiceMessagePreprocessingComponent(DomainServiceContentFilterComponent contentFilterComponent) {
        this.contentFilterComponent = contentFilterComponent;
    }

    public void prepareMessageForRouting(DomainEntitySMSMessage message) throws DomainExceptionValidationException {
        try {
            message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);
            message.setUpdatedAt(Timestamp.valueOf(SharedUtilDateUtil.getCurrentTimestamp()));
            formatMessageContent(message);
            augmentMessageWithMetadata(message);
        } catch (Exception e) {
            throw new DomainExceptionValidationException("Failed to prepare message for routing", e);
        }
    }

    public void formatMessageContent(DomainEntitySMSMessage message) throws DomainExceptionValidationException {
        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new DomainExceptionValidationException("Content is empty or null.");
        }
        String content = message.getContent().trim();
        if (contentFilterComponent.checkForProhibitedContent(content)) {
            throw new DomainExceptionValidationException("Message content contains prohibited content.");
        }
        if (!contentFilterComponent.validateContentLength(content)) {
            throw new DomainExceptionValidationException("Message content exceeds allowed length.");
        }
        content = encodeContent(content);
        message.setContent(content);
    }

    private String encodeContent(String content) {
        return content.toUpperCase();
    }

    private void augmentMessageWithMetadata(DomainEntitySMSMessage message) {
        Map<String, String> metadata = message.getMetadata();
        if (metadata != null) {
            metadata.put("route", determineRoutingPath(message));
            metadata.put("routingKey", generateRoutingKey(message));
            metadata.put("routingTimestamp", SharedUtilDateUtil.getCurrentTimestamp());
        }
    }

    private String determineRoutingPath(DomainEntitySMSMessage message) {
        String recipientNumber = message.getRecipientNumber();
        return recipientNumber.startsWith("+1") ? "route_us" : "route_international";
    }

    private String generateRoutingKey(DomainEntitySMSMessage message) {
        return "ROUTE-" + message.getRecipientNumber();
    }
}