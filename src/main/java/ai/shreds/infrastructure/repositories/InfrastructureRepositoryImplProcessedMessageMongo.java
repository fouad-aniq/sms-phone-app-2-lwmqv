package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortProcessedMessageRepository;
import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class InfrastructureRepositoryImplProcessedMessageMongo implements DomainPortProcessedMessageRepository {

    @Autowired
    private ProcessedMessageMongoRepository processedMessageMongoRepository;

    @Override
    public void save(DomainEntityProcessedMessage processedMessage) {
        try {
            processedMessageMongoRepository.save(processedMessage);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error saving processed message: " + e.getMessage());
        }
    }

    @Override
    public DomainEntityProcessedMessage findById(String id) {
        try {
            Optional<DomainEntityProcessedMessage> optionalProcessedMessage = processedMessageMongoRepository.findById(id);
            return optionalProcessedMessage.orElse(null);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error finding processed message by id: " + e.getMessage());
        }
    }

    @Override
    public DomainEntityProcessedMessage findByOriginalMessageId(String originalMessageId) {
        try {
            Optional<DomainEntityProcessedMessage> result = processedMessageMongoRepository.findByOriginalMessageId(originalMessageId);
            return result.orElse(null);
        } catch (Exception e) {
            throw new InfrastructureExceptionDataAccessException("Error finding processed message by originalMessageId: " + e.getMessage());
        }
    }

    interface ProcessedMessageMongoRepository extends MongoRepository<DomainEntityProcessedMessage, String> {
        @Query("{ 'originalMessageId' : ?0 }")
        Optional<DomainEntityProcessedMessage> findByOriginalMessageId(String originalMessageId);
    }

}