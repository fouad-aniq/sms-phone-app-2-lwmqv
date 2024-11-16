package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class InfrastructureRepositoryImplSMSMessageRepository implements DomainPortSMSMessageRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(DomainEntitySMSMessage message) {
        try {
            entityManager.persist(message);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Failed to save SMS message", e);
        }
    }

    @Override
    public Optional<DomainEntitySMSMessage> findByMessageId(String messageId) {
        try {
            TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
                    "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
            query.setParameter("messageId", messageId);
            DomainEntitySMSMessage message = query.getSingleResult();
            return Optional.of(message);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Failed to find SMS message by messageId", e);
        }
    }

    @Override
    @Transactional
    public void updateStatus(String messageId, SharedEnumMessageStatusEnum status) {
        try {
            TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
                    "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
            query.setParameter("messageId", messageId);
            DomainEntitySMSMessage message = query.getSingleResult();
            message.setStatus(status);
            entityManager.merge(message);
        } catch (NoResultException e) {
            throw new InfrastructureExceptionDataAccessException("SMS message not found with messageId: " + messageId, e);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Failed to update SMS message status", e);
        }
    }
}
