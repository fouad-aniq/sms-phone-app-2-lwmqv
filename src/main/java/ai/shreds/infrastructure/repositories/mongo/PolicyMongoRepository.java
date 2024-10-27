package ai.shreds.infrastructure.repositories.mongo;

import ai.shreds.infrastructure.repositories.mongo.documents.PolicyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolicyMongoRepository extends MongoRepository<PolicyDocument, String> {
    // Additional query methods can be defined here if needed
}