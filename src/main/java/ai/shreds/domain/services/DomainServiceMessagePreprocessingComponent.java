package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.exceptions.DomainExceptionValidationException;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedUtilDateUtil;
import java.sql.Timestamp;
import java.util.Map;

/**
 * DomainServiceMessagePreprocessingComponent
 * 
 * Prepares DomainEntitySMSMessage objects for routing by formatting
 * and enhancing them with necessary metadata.
 * Adheres to Hexagonal Architecture principles.
 */
public class DomainServiceMessagePreprocessingComponent {

    private final DomainServiceContentFilterComponent contentFilterComponent;
    // Include additional components if necessary, e.g., phone number formatter

    public DomainServiceMessagePreprocessingComponent(
            DomainServiceContentFilterComponent contentFilterComponent) {
        this.contentFilterComponent = contentFilterComponent;
    }

    /**
     * Prepares the message for routing.
     *
     * @param message The message to prepare.
     * @throws DomainExceptionValidationException if the message cannot be prepared.
     */
    public void prepareMessageForRouting(DomainEntitySMSMessage message) throws DomainExceptionValidationException {
        // Update message status to VALIDATED
        message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);
        message.setUpdatedAt(Timestamp.valueOf(SharedUtilDateUtil.getCurrentTimestamp()));

        // Format message content
        formatMessageContent(message);

        // Enhance message with metadata required for routing
        augmentMessageWithMetadata(message);
    }

    /**
     * Formats the message content as needed for downstream processing.
     *
     * @param message The message to format.
     * @throws DomainExceptionValidationException if the content is invalid.
     */
    public void formatMessageContent(DomainEntitySMSMessage message) throws DomainExceptionValidationException {
        String content = message.getContent();

        // Check for prohibited content
        if (contentFilterComponent.checkForProhibitedContent(content)) {
            throw new DomainExceptionValidationException("Message content contains prohibited content.");
        }

        // Validate content length
        if (!contentFilterComponent.validateContentLength(content)) {
            throw new DomainExceptionValidationException("Message content exceeds allowed length.");
        }

        // Trim content
        content = content.trim();

        // Apply encoding or structural changes (e.g., convert to uppercase)
        content = encodeContent(content);

        // Update message content
        message.setContent(content);
    }

    /**
     * Encodes or modifies the content as needed.
     *
     * @param content The content to encode.
     * @return The encoded content.
     */
    private String encodeContent(String content) {
        // Implement encoding or structural changes as needed
        // Example: convert to uppercase
        return content.toUpperCase();
    }

    /**
     * Enhances the message with additional metadata required for routing.
     *
     * @param message The message to augment.
     */
    private void augmentMessageWithMetadata(DomainEntitySMSMessage message) {
        Map<String, String> metadata = message.getMetadata();

        // Add routing information
        String route = determineRoutingPath(message);
        metadata.put("route", route);

        // Add routing key
        String routingKey = generateRoutingKey(message);
        metadata.put("routingKey", routingKey);

        // Add routing timestamp
        String routingTimestamp = SharedUtilDateUtil.getCurrentTimestamp();
        metadata.put("routingTimestamp", routingTimestamp);

        // Update metadata in the message
        message.setMetadata(metadata);
    }

    /**
     * Determines the routing path based on message properties.
     *
     * @param message The message to route.
     * @return The routing path.
     */
    private String determineRoutingPath(DomainEntitySMSMessage message) {
        // Implement logic to determine routing path
        // Example: based on recipient number or content
        String recipientNumber = message.getRecipientNumber();
        if (recipientNumber.startsWith("+1")) {
            return "route_us";
        } else {
            return "route_international";
        }
    }

    /**
     * Generates a routing key based on message properties.
     *
     * @param message The message to use.
     * @return The generated routing key.
     */
    private String generateRoutingKey(DomainEntitySMSMessage message) {
        // Logic to generate a routing key based on message properties
        return "ROUTE-" + message.getRecipientNumber();
    }
}
