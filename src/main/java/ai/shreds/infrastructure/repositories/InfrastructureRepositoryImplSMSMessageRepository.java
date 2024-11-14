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
import java.util.List;

@Repository
public class InfrastructureRepositoryImplSMSMessageRepository implements DomainPortSMSMessageRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(DomainEntitySMSMessage message) {
        if (messageExists(message.getMessageId())) {
            throw new InfrastructureExceptionDataAccessException("Message with ID " + message.getMessageId() + " already exists.");
        }
        entityManager.persist(message);
    }

    private boolean messageExists(String messageId) {
        TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
            "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
        query.setParameter("messageId", messageId);
        return !query.getResultList().isEmpty();
    }

    @Override
    public Optional<DomainEntitySMSMessage> findByMessageId(String messageId) {
        TypedQuery<DomainEntitySMSMessage> query = entityManager.createQuery(
            "SELECT m FROM DomainEntitySMSMessage m WHERE m.messageId = :messageId", DomainEntitySMSMessage.class);
        query.setParameter("messageId", messageId);
        List<DomainEntitySMSMessage> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    @Transactional
    public void updateStatus(String messageId, SharedEnumMessageStatusEnum status) {
        DomainEntitySMSMessage message = findByMessageId(messageId).orElseThrow(() ->
            new InfrastructureExceptionDataAccessException("Message with ID " + messageId + " not found."));
        message.setStatus(status);
        entityManager.merge(message);
    }
}