package ai.shreds.infrastructure.repositories;

import ai.shreds.application.ports.ApplicationOutputPortMessageRepositoryPort;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDeliveryDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InfrastructureRepositoryImplProcessedMessage implements ApplicationOutputPortMessageRepositoryPort {

    private final ProcessedMessageMongoRepository processedMessageMongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(DomainEntityProcessedMessage message) {
        ProcessedMessageDocument document = ProcessedMessageDocument.fromDomainEntity(message);
        processedMessageMongoRepository.save(document);
    }

    @Override
    public DomainEntityProcessedMessage findById(String id) {
        Optional<ProcessedMessageDocument> documentOptional = processedMessageMongoRepository.findById(id);
        return documentOptional.map(ProcessedMessageDocument::toDomainEntity).orElse(null);
    }

    @Override
    public List<DomainEntityProcessedMessage> findByDispatchStatus(String status) {
        List<ProcessedMessageDocument> documents = processedMessageMongoRepository.findByDispatchStatus(status);
        return documents.stream()
                .map(ProcessedMessageDocument::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDispatchStatus(String id, String status) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("dispatchStatus", status);
        mongoTemplate.updateFirst(query, update, ProcessedMessageDocument.class);
    }

    @Override
    public void incrementRetryCount(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("retryCount", 1);
        mongoTemplate.updateFirst(query, update, ProcessedMessageDocument.class);
    }

    @Override
    public void updateDeliveryDetails(String id, DomainValueDeliveryDetails details) {
        Query query = a Query(Criteria.where("id").is(id));
        Update update = new Update()
                .set("deliveryDetails.providerResponseCode", details.getProviderResponseCode())
                .set("deliveryDetails.deliveryReceiptDetails", details.getDeliveryReceiptDetails())
                .set("deliveryDetails.failureReason", details.getFailureReason());
        mongoTemplate.updateFirst(query, update, ProcessedMessageDocument.class);
    }
}