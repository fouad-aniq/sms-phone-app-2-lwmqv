package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.domain.ports.DomainPortMessageRequestRepository;
import ai.shreds.infrastructure.repositories.entities.MessageRequestEntity;
import ai.shreds.infrastructure.repositories.mappers.MessageRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
    public Mono<DomainEntityMessageRequest> findById(String messageRequestId) {
        return Mono.fromCallable(() -> messageRequestMongoRepository.findById(messageRequestId))
                .map(optionalEntity -> optionalEntity.map(messageRequestMapper::toDomain).orElse(null));
    }

    @Override
    public Mono<Void> updateStatus(String messageRequestId, String status) {
        return Mono.fromRunnable(() -> {
            Optional<MessageRequestEntity> optionalEntity = messageRequestMongoRepository.findById(messageRequestId);
            optionalEntity.ifPresent(entity -> {
                entity.setStatus(status);
                messageRequestMongoRepository.save(entity);
            });
        });
    }

    @Override
    public Flux<DomainEntityMessageRequest> findAll() {
        return Flux.fromIterable(messageRequestMongoRepository.findAll())
                .map(messageRequestMapper::toDomain);
    }
}