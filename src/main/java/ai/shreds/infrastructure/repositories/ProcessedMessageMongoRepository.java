package ai.shreds.infrastructure.repositories;

import ai.shreds.infrastructure.repositories.ProcessedMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProcessedMessageMongoRepository extends MongoRepository<ProcessedMessageDocument, String> {
    List<ProcessedMessageDocument> findByDispatchStatus(String status);
}