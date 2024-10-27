package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.application.ports.ApplicationOutputPortMessageRepositoryPort; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
 import ai.shreds.shared.SharedEnumDeliveryStatus; 
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
         Query query = new Query(Criteria.where("id").is(id)); 
         Update update = new Update() 
                 .set("deliveryDetails.providerResponseCode", details.getProviderResponseCode()) 
                 .set("deliveryDetails.deliveryReceiptDetails", details.getDeliveryReceiptDetails()) 
                 .set("deliveryDetails.failureReason", details.getFailureReason()); 
         mongoTemplate.updateFirst(query, update, ProcessedMessageDocument.class); 
     } 
 } 
  
 // ProcessedMessageMongoRepository.java 
 package ai.shreds.infrastructure.repositories; 
  
 import org.springframework.data.mongodb.repository.MongoRepository; 
 import org.springframework.stereotype.Repository; 
  
 import java.util.List; 
  
 @Repository 
 public interface ProcessedMessageMongoRepository extends MongoRepository<ProcessedMessageDocument, String> { 
     List<ProcessedMessageDocument> findByDispatchStatus(String dispatchStatus); 
 } 
  
 // ProcessedMessageDocument.java 
 package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
 import ai.shreds.shared.SharedEnumDeliveryStatus; 
 import lombok.Builder; 
 import lombok.Data; 
 import org.springframework.data.annotation.Id; 
 import org.springframework.data.mongodb.core.mapping.Document; 
  
 import java.time.LocalDateTime; 
 import java.util.List; 
  
 @Data 
 @Builder 
 @Document(collection = "processed_messages") 
 public class ProcessedMessageDocument { 
     @Id 
     private String id; 
     private String originalMessageId; 
     private String personalizedContent; 
     private Boolean validationStatus; 
     private List<String> businessRulesApplied; 
     private Boolean preparedForDispatch; 
     private String dispatchStatus; 
     private LocalDateTime dispatchTimestamp; 
     private DeliveryDetailsDocument deliveryDetails; 
     private Integer retryCount; 
     private LocalDateTime createdAt; 
     private LocalDateTime updatedAt; 
  
     public static ProcessedMessageDocument fromDomainEntity(DomainEntityProcessedMessage domainEntity) { 
         return ProcessedMessageDocument.builder() 
                 .id(domainEntity.getId()) 
                 .originalMessageId(domainEntity.getOriginalMessageId()) 
                 .personalizedContent(domainEntity.getPersonalizedContent()) 
                 .validationStatus(domainEntity.getValidationStatus()) 
                 .businessRulesApplied(domainEntity.getBusinessRulesApplied()) 
                 .preparedForDispatch(domainEntity.getPreparedForDispatch()) 
                 .dispatchStatus(domainEntity.getDispatchStatus().name()) 
                 .dispatchTimestamp(domainEntity.getDispatchTimestamp()) 
                 .deliveryDetails(DeliveryDetailsDocument.fromDomainValue(domainEntity.getDeliveryDetails())) 
                 .retryCount(domainEntity.getRetryCount()) 
                 .createdAt(domainEntity.getCreatedAt()) 
                 .updatedAt(domainEntity.getUpdatedAt()) 
                 .build(); 
     } 
  
     public DomainEntityProcessedMessage toDomainEntity() { 
         return DomainEntityProcessedMessage.builder() 
                 .id(this.id) 
                 .originalMessageId(this.originalMessageId) 
                 .personalizedContent(this.personalizedContent) 
                 .validationStatus(this.validationStatus) 
                 .businessRulesApplied(this.businessRulesApplied) 
                 .preparedForDispatch(this.preparedForDispatch) 
                 .dispatchStatus(SharedEnumDeliveryStatus.valueOf(this.dispatchStatus)) 
                 .dispatchTimestamp(this.dispatchTimestamp) 
                 .deliveryDetails(this.deliveryDetails != null ? this.deliveryDetails.toDomainValue() : null) 
                 .retryCount(this.retryCount) 
                 .createdAt(this.createdAt) 
                 .updatedAt(this.updatedAt) 
                 .build(); 
     } 
 } 
  
 // DeliveryDetailsDocument.java 
 package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
 import lombok.Builder; 
 import lombok.Data; 
  
 @Data 
 @Builder 
 public class DeliveryDetailsDocument { 
     private String providerResponseCode; 
     private String deliveryReceiptDetails; 
     private String failureReason; 
  
     public static DeliveryDetailsDocument fromDomainValue(DomainValueDeliveryDetails domainValue) { 
         if (domainValue == null) { 
             return null; 
         } 
         return DeliveryDetailsDocument.builder() 
                 .providerResponseCode(domainValue.getProviderResponseCode()) 
                 .deliveryReceiptDetails(domainValue.getDeliveryReceiptDetails()) 
                 .failureReason(domainValue.getFailureReason()) 
                 .build(); 
     } 
  
     public DomainValueDeliveryDetails toDomainValue() { 
         return DomainValueDeliveryDetails.builder() 
                 .providerResponseCode(this.providerResponseCode) 
                 .deliveryReceiptDetails(this.deliveryReceiptDetails) 
                 .failureReason(this.failureReason()) 
                 .build(); 
     } 
 } 
 