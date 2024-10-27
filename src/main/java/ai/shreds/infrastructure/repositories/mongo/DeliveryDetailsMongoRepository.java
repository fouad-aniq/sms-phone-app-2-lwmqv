package ai.shreds.infrastructure.repositories.mongo;

import ai.shreds.infrastructure.documents.DeliveryDetailsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryDetailsMongoRepository extends MongoRepository<DeliveryDetailsDocument, String> {

    DeliveryDetailsDocument findByMessageId(String messageId);
}