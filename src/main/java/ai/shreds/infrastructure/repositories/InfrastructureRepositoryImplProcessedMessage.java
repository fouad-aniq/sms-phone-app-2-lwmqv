package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import ai.shreds.domain.ports.DomainPortMessageRepository;
import ai.shreds.infrastructure.mongodb.entities.ProcessedMessageMongoEntity;
import ai.shreds.infrastructure.mongodb.repositories.ProcessedMessageMongoRepository;
import ai.shreds.infrastructure.mongodb.mappers.ProcessedMessageEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InfrastructureRepositoryImplProcessedMessage implements DomainPortMessageRepository {

    @Autowired
    private ProcessedMessageMongoRepository processedMessageMongoRepository;

    @Autowired
    private ProcessedMessageEntityMapper processedMessageEntityMapper;

    @Override
    public void save(DomainEntityProcessedMessage message) {
        ProcessedMessageMongoEntity entity = processedMessageEntityMapper.toMongoEntity(message);
        processedMessageMongoRepository.save(entity);
    }

    @Override
    public DomainEntityProcessedMessage findById(String messageId) {
        Optional<ProcessedMessageMongoEntity> optionalEntity = processedMessageMongoRepository.findById(messageId);
        return optionalEntity.map(processedMessageEntityMapper::toDomainEntity).orElse(null);
    }

    @Override
    public List<DomainEntityProcessedMessage> findByDispatchStatus(DomainValueDispatchStatus status) {
        List<ProcessedMessageMongoEntity> entities = processedMessageMongoRepository.findByDispatchStatus(status.name());
        return entities.stream()
                .map(processedMessageEntityMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String messageId) {
        processedMessageMongoRepository.deleteById(messageId);
    }

    @Override
    public Integer countByDispatchStatus(DomainValueDispatchStatus status) {
        return processedMessageMongoRepository.countByDispatchStatus(status.name());
    }
}