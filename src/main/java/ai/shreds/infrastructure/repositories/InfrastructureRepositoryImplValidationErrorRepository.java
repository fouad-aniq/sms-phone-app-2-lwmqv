package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

@Repository
public class InfrastructureRepositoryImplValidationErrorRepository implements DomainPortValidationErrorRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(DomainEntityValidationError validationError) {
        try {
            entityManager.persist(validationError);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Failed to save ValidationError", e);
        }
    }

    @Override
    public List<DomainEntityValidationError> findByMessageId(String messageId) {
        try {
            TypedQuery<DomainEntityValidationError> query = entityManager.createQuery(
                "SELECT ve FROM DomainEntityValidationError ve WHERE ve.messageId = :messageId",
                DomainEntityValidationError.class
            );
            query.setParameter("messageId", messageId);
            return query.getResultList();
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Failed to retrieve ValidationErrors by messageId", e);
        }
    }
}
