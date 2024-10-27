package ai.shreds.infrastructure.repositories;

import ai.shreds.application.ports.ApplicationOutputPortMessageRequestRepositoryPort;
import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.infrastructure.repositories.documents.MessageRequestDocument;
import ai.shreds.infrastructure.repositories.mappers.MessageRequestMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class InfrastructureRepositoryImplMessageRequest implements ApplicationOutputPortMessageRequestRepositoryPort {

    private final MessageRequestMongoRepository messageRequestMongoRepository;
    private final MessageRequestMapper messageRequestMapper;

    public InfrastructureRepositoryImplMessageRequest(MessageRequestMongoRepository messageRequestMongoRepository,
                                                    MessageRequestMapper messageRequestMapper) {
        this.messageRequestMongoRepository = messageRequestMongoRepository;
        this.messageRequestMapper = messageRequestMapper;
    }

    @Override
    public void save(DomainEntityMessageRequest messageRequest) {
        MessageRequestDocument document = messageRequestMapper.toDocument(messageRequest);
        messageRequestMongoRepository.save(document);
    }

    @Override
    public DomainEntityMessageRequest findById(String id) {
        return messageRequestMongoRepository.findById(id)
            .map(messageRequestMapper::toDomainEntity)
            .orElseThrow(() -> new NoSuchElementException("MessageRequest not found with id: " + id));
    }

    @Override
    public void updateStatus(String id, String status) {
        MessageRequestDocument document = messageRequestMongoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("MessageRequest not found with id: " + id));
        document.setStatus(status);
        messageRequestMongoRepository.save(document);
    }

    @Override
    public List<DomainEntityMessageRequest> findByStatus(String status) {
        List<MessageRequestDocument> documents = messageRequestMongoRepository.findByStatus(status);
        return documents.stream()
                .map(messageRequestMapper::toDomainEntity)
                .collect(Collectors.toList());
    }
}