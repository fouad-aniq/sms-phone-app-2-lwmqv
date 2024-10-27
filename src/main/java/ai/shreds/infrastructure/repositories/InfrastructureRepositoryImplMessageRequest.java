package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.ports.DomainPortMessageRequestRepository;
import ai.shreds.infrastructure.repositories.entities.MessageRequestEntity;
import ai.shreds.infrastructure.repositories.mappers.MessageRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InfrastructureRepositoryImplMessageRequest implements DomainPortMessageRequestRepository {

    private final MessageRequestMongoRepository messageRequestMongoRepository;
    private final MessageRequestMapper messageRequestMapper;

    @Autowired
    public InfrastructureRepositoryImplMessageRequest(MessageRequestMongoRepository messageRequestMongoRepository,
                                                      MessageRequestMapper messageRequestMapper) {
        this.messageRequestMongoRepository = messageRequestMongoRepository;
        this.messageRequestMapper = messageRequestMapper;
    }

    @Override
    public DomainEntityMessageRequest findById(String messageRequestId) {
        Optional<MessageRequestEntity> entity = messageRequestMongoRepository.findById(messageRequestId);
        return entity.map(messageRequestMapper::toDomain).orElse(null);
    }

    @Override
    public void updateStatus(String messageRequestId, String status) {
        messageRequestMongoRepository.findById(messageRequestId).ifPresent(entity -> {
            entity.setStatus(status);
            messageRequestMongoRepository.save(entity);
        });
    }

    @Override
    public List<DomainEntityMessageRequest> findAll() {
        return messageRequestMongoRepository.findAll().stream()
                .map(messageRequestMapper::toDomain)
                .collect(Collectors.toList());
    }

}