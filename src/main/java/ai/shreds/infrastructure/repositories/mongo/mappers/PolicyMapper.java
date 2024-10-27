package ai.shreds.infrastructure.repositories.mongo.mappers;

import ai.shreds.domain.entities.DomainEntityPolicy;
import ai.shreds.infrastructure.repositories.mongo.documents.PolicyDocument;

import java.util.List;
import java.util.stream.Collectors;

public class PolicyMapper {

    public List<DomainEntityPolicy> toDomainEntities(List<PolicyDocument> documents) {
        return documents.stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    public DomainEntityPolicy toDomainEntity(PolicyDocument document) {
        if (document == null) {
            return null;
        }
        return new DomainEntityPolicy(
                document.getId(),
                document.getName(),
                document.getDescription(),
                document.getCriteria(),
                document.getIsActive()
        );
    }
}