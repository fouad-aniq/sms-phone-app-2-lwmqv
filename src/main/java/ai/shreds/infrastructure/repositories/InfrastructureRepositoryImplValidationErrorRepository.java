package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InfrastructureRepositoryImplValidationErrorRepository implements DomainPortValidationErrorRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(DomainEntityValidationError validationError) {
        try {
            if (validationError.getErrorCode() == null || validationError.getErrorMessage() == null) {
                throw new IllegalArgumentException("Error code and message must be provided.");
            }
            entityManager.persist(validationError);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error saving ValidationError.", e);
        }
    }

    @Override
    public List<DomainEntityValidationError> findByMessageId(String messageId) {
        try {
            TypedQuery<DomainEntityValidationError> query = entityManager.createQuery(
                "SELECT v FROM DomainEntityValidationError v WHERE v.messageId = :messageId",
                DomainEntityValidationError.class);
            query.setParameter("messageId", messageId);
            return query.getResultList();
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error retrieving ValidationErrors for messageId: " + messageId, e);
        }
    }
}