package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDeliveryDetails;
import ai.shreds.shared.SharedEnumDeliveryStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DomainServiceMessageProcessor {

    public DomainEntityProcessedMessage processMessage(DomainEntityMessageRequest messageRequest) {
        DomainEntityProcessedMessage processedMessage = new DomainEntityProcessedMessage();

        processedMessage.setId(UUID.randomUUID().toString());
        processedMessage.setOriginalMessageId(messageRequest.getId());

        boolean isValid = validateMessageContent(messageRequest.getContent());
        processedMessage.setValidationStatus(isValid);

        if (isValid) {
            String personalizedContent = personalizeContent(messageRequest.getContent());
            processedMessage.setPersonalizedContent(personalizedContent);

            List<String> businessRulesApplied = applyBusinessRules(messageRequest);
            processedMessage.setBusinessRulesApplied(businessRulesApplied);

            processedMessage.setPreparedForDispatch(true);
            processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.PENDING);
        } else {
            processedMessage.setPreparedForDispatch(false);
            processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.FAILED);
        }

        processedMessage.setRetryCount(0);

        LocalDateTime now = LocalDateTime.now();
        processedMessage.setCreatedAt(now);
        processedMessage.setUpdatedAt(now);

        processedMessage.setDeliveryDetails(new DomainValueDeliveryDetails());

        return processedMessage;
    }

    private boolean validateMessageContent(String content) {
        return content != null && !content.trim().isEmpty();
    }

    private String personalizeContent(String content) {
        // Actual personalization logic
        return "Personalized: " + content;
    }

    private List<String> applyBusinessRules(DomainEntityMessageRequest messageRequest) {
        List<String> rulesApplied = new ArrayList<>();
        rulesApplied.add("Processed standard business rules");
        // Add more rules as necessary
        return rulesApplied;
    }
}