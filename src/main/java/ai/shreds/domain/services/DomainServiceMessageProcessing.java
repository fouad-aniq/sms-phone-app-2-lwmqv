package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortMessageRequestRepository;
import ai.shreds.domain.ports.DomainPortProcessedMessageRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainServiceMessageProcessing {

    private final DomainPortMessageRequestRepository messageRequestRepository;
    private final DomainPortProcessedMessageRepository processedMessageRepository;
    private final DomainServicePolicyValidation policyValidationService;
    private final DomainServiceBusinessRule businessRuleService;

    public DomainEntityProcessedMessage processMessage(DomainEntityMessageRequest messageRequest) {
        messageRequestRepository.save(messageRequest);
        String personalizedContent = personalizeContent(messageRequest);
        boolean validationStatus = policyValidationService.validate(messageRequest);
        List<String> businessRulesApplied = businessRuleService.applyRules(messageRequest);
        boolean preparedForDispatch = validationStatus;
        DomainEntityProcessedMessage processedMessage = new DomainEntityProcessedMessage();
        processedMessage.setId(UUID.randomUUID().toString());
        processedMessage.setOriginalMessageId(messageRequest.getId());
        processedMessage.setPersonalizedContent(personalizedContent);
        processedMessage.setValidationStatus(validationStatus);
        processedMessage.setBusinessRulesApplied(businessRulesApplied);
        processedMessage.setPreparedForDispatch(preparedForDispatch);
        processedMessage.setTimestamp(Instant.now());
        processedMessageRepository.save(processedMessage);
        return processedMessage;
    }

    private String personalizeContent(DomainEntityMessageRequest messageRequest) {
        String content = messageRequest.getContent();
        if (messageRequest.getMetadata() != null && messageRequest.getMetadata() instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> metadata = (Map<String, Object>) messageRequest.getMetadata();
            for (Map.Entry<String, Object> entry : metadata.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String value = entry.getValue().toString();
                content = content.replace(placeholder, value);
            }
        }
        return content;
    }
}