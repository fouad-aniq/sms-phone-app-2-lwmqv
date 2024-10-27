package domain.services;

import java.util.List;
import java.util.stream.Collectors;
import domain.entities.DomainEntityMessageRequest;
import domain.entities.DomainEntityBusinessRule;
import domain.ports.DomainPortBusinessRuleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainServiceBusinessRule {
    private final DomainPortBusinessRuleRepository businessRuleRepository;

    public DomainEntityMessageRequest applyRules(DomainEntityMessageRequest messageRequest) {
        List<DomainEntityBusinessRule> activeRules = businessRuleRepository.findAll().stream()
            .filter(DomainEntityBusinessRule::isActive)
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