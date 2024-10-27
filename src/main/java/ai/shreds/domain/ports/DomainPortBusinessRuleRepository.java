package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityBusinessRule;
import java.util.List;

public interface DomainPortBusinessRuleRepository {
    List<DomainEntityBusinessRule> findAll();
    DomainEntityBusinessRule findById(String id);
}