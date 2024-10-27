package ai.shreds.infrastructure.repositories;

import ai.shreds.infrastructure.repositories.entities.MessageRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRequestMongoRepository extends MongoRepository<MessageRequestEntity, String> {
    // No additional methods are needed beyond those provided by MongoRepository.
}