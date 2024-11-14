package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class InfrastructureRepositoryImplSMSMessageRepository implements DomainPortSMSMessageRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(DomainEntitySMSMessage message) {
        try {
            // Check if a message with the same messageId already exists
            TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
                "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
            query.setParameter("messageId", message.getMessageId());
            DomainEntitySMSMessage existingMessage = null;
            try {
                existingMessage = query.getSingleResult();
            } catch (NoResultException e) {
                // Message does not exist, safe to proceed
            }

            if (existingMessage != null) {
                // Message with the same messageId already exists
                throw new InfrastructureExceptionDataAccessException("Message with ID " + message.getMessageId() + " already exists.");
            } else {
                // Persist new message
                entityManager.persist(message);
            }
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error saving SMS Message", e);
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
            throw new InfrastructureExceptionDataAccessException("Error finding SMS Message with ID " + messageId, e);
        }
    }

    @Override
    @Transactional
    public void updateStatus(String messageId, SharedEnumMessageStatusEnum status) {
        try {
            TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
                "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
            query.setParameter("messageId", messageId);
            DomainEntitySMSMessage message = null;
            try {
                message = query.getSingleResult();
                message.setStatus(status);
                entityManager.merge(message);
            } catch (NoResultException e) {
                throw new InfrastructureExceptionDataAccessException("Message with ID " + messageId + " not found.");
            }
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error updating status for SMS Message with ID " + messageId, e);
        }
    }
}
