package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.entities.DomainEntityPolicy;
import ai.shreds.domain.ports.DomainPortPolicyRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DomainServicePolicyValidation {
    private final DomainPortPolicyRepository policyRepository;

    public Boolean validate(DomainEntityMessageRequest messageRequest) {
        List<DomainEntityPolicy> policies = policyRepository.findAll();

        for (DomainEntityPolicy policy : policies) {
            if (Boolean.TRUE.equals(policy.getIsActive())) {
                String criteria = policy.getCriteria();
                Pattern pattern = Pattern.compile(criteria);
                if (pattern.matcher(messageRequest.getContent()).find()) {
                    return false; // Message content violates the policy criteria
                }
            }
        }
        return true; // Message passes all active policies
    }
}