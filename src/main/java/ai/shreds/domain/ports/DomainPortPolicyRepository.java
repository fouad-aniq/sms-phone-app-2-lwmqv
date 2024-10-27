package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityPolicy;
import java.util.List;

public interface DomainPortPolicyRepository {
    List<DomainEntityPolicy> findAll();
    DomainEntityPolicy findById(String id);
}