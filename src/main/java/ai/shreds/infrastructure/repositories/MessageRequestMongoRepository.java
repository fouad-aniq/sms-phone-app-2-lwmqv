package ai.shreds.infrastructure.repositories;

import ai.shreds.infrastructure.repositories.documents.MessageRequestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MessageRequestMongoRepository extends MongoRepository<MessageRequestDocument, String> {
    List<MessageRequestDocument> findByStatus(String status);
}