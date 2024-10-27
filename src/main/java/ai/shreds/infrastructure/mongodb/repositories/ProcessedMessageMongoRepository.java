package ai.shreds.infrastructure.mongodb.repositories;

import ai.shreds.infrastructure.mongodb.entities.ProcessedMessageMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for accessing processed messages in MongoDB.
 */
public interface ProcessedMessageMongoRepository extends MongoRepository<ProcessedMessageMongoEntity, String> {

    /**
     * Finds a list of processed message entities by their dispatch status.
     *
     * @param status the dispatch status to filter by
     * @return a list of processed message entities with the given dispatch status
     */
    List<ProcessedMessageMongoEntity> findByDispatchStatus(String status);

    /**
     * Counts the number of processed message entities by their dispatch status.
     *
     * @param status the dispatch status to filter by
     * @return the count of processed message entities with the given dispatch status
     */
    Integer countByDispatchStatus(String status);
}