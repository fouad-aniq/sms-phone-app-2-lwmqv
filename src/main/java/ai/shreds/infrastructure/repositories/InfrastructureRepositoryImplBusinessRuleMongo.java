package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityBusinessRule;
import ai.shreds.domain.ports.DomainPortBusinessRuleRepository;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InfrastructureRepositoryImplBusinessRuleMongo implements DomainPortBusinessRuleRepository {

    @Autowired
    private BusinessRuleMongoRepository businessRuleMongoRepository;

    @Override
    public List<DomainEntityBusinessRule> findAll() {
        try {
            List<BusinessRuleDocument> documents = businessRuleMongoRepository.findByIsActiveTrue();
            return documents.stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new InfrastructureExceptionDataAccessException("Error retrieving all active BusinessRules: " + e.getMessage());
        }
    }

    @Override
    public DomainEntityBusinessRule findById(String id) {
        try {
            Optional<BusinessRuleDocument> optionalDocument = businessRuleMongoRepository.findById(id);
            return optionalDocument.map(this::mapToDomainEntity).orElse(null);
        } catch (DataAccessException e) {
            throw new InfrastructureExceptionDataAccessException("Error retrieving BusinessRule by id: " + e.getMessage());
        }
    }

    private DomainEntityBusinessRule mapToDomainEntity(BusinessRuleDocument document) {
        DomainEntityBusinessRule entity = new DomainEntityBusinessRule();
        entity.setId(document.getId());
        entity.setName(document.getName());
        entity.setDescription(document.getDescription());
        entity.setLogic(document.getLogic());
        entity.setIsActive(document.getIsActive());
        return entity;
    }

    interface BusinessRuleMongoRepository extends MongoRepository<BusinessRuleDocument, String> {
        List<BusinessRuleDocument> findByIsActiveTrue();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "business_rules")
    static class BusinessRuleDocument {
        @Id
        private String id;
        private String name;
        private String description;
        private String logic;
        private Boolean isActive;
    }
}