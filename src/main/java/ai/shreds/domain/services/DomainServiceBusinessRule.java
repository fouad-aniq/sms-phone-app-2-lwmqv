package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.entities.DomainEntityBusinessRule;
import ai.shreds.domain.ports.DomainPortBusinessRuleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DomainServiceBusinessRule {
    private final DomainPortBusinessRuleRepository businessRuleRepository;

    public DomainEntityMessageRequest applyRules(DomainEntityMessageRequest messageRequest) {
        List<DomainEntityBusinessRule> activeRules = businessRuleRepository.findAll().stream()
            .filter(DomainEntityBusinessRule::getIsActive)
            .collect(Collectors.toList());

        for (DomainEntityBusinessRule rule : activeRules) {
            messageRequest = applyRuleLogic(rule, messageRequest);
        }
        return messageRequest;
    }

    private DomainEntityMessageRequest applyRuleLogic(DomainEntityBusinessRule rule, DomainEntityMessageRequest messageRequest) {
        // Example logic to apply the rule
        if (rule.getLogic().equalsIgnoreCase("uppercase")) {
            messageRequest.setContent(messageRequest.getContent().toUpperCase());
        } else if (rule.getLogic().equalsIgnoreCase("lowercase")) {
            messageRequest.setContent(messageRequest.getContent().toLowerCase());
        }
        // Add more rule logic as needed
        return messageRequest;
    }
}