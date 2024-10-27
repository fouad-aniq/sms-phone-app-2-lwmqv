package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.ports.DomainPortMessageRequestRepository; 
 import ai.shreds.domain.ports.DomainPortProcessedMessageRepository; 
 import ai.shreds.domain.services.DomainServiceBusinessRule; 
 import ai.shreds.domain.services.DomainServicePolicyValidation; 
  
 import java.time.Instant; 
 import java.util.List; 
 import java.util.UUID; 
  
 public class DomainServiceMessageProcessing { 
  
     private final DomainPortMessageRequestRepository messageRequestRepository; 
     private final DomainPortProcessedMessageRepository processedMessageRepository; 
     private final DomainServicePolicyValidation policyValidationService; 
     private final DomainServiceBusinessRule businessRuleService; 
  
     public DomainServiceMessageProcessing(DomainPortMessageRequestRepository messageRequestRepository, 
                                           DomainPortProcessedMessageRepository processedMessageRepository, 
                                           DomainServicePolicyValidation policyValidationService, 
                                           DomainServiceBusinessRule businessRuleService) { 
         this.messageRequestRepository = messageRequestRepository; 
         this.processedMessageRepository = processedMessageRepository; 
         this.policyValidationService = policyValidationService; 
         this.businessRuleService = businessRuleService; 
     } 
  
     public DomainEntityProcessedMessage processMessage(DomainEntityMessageRequest messageRequest) { 
         // Save the original MessageRequest 
         messageRequestRepository.save(messageRequest); 
  
         // Step 1: Personalize Content 
         String personalizedContent = personalizeContent(messageRequest); 
  
         // Step 2: Validate Content 
         boolean validationStatus = policyValidationService.validate(messageRequest); 
  
         // Step 3: Apply Business Rules 
         List<String> businessRulesApplied = businessRuleService.applyRules(messageRequest); 
  
         // Step 4: Prepare for Dispatch 
         boolean preparedForDispatch = validationStatus; 
  
         // Step 5: Create ProcessedMessage 
         DomainEntityProcessedMessage processedMessage = new DomainEntityProcessedMessage(); 
         processedMessage.setId(UUID.randomUUID().toString()); 
         processedMessage.setOriginalMessageId(messageRequest.getId()); 
         processedMessage.setPersonalizedContent(personalizedContent); 
         processedMessage.setValidationStatus(validationStatus); 
         processedMessage.setBusinessRulesApplied(businessRulesApplied); 
         processedMessage.setPreparedForDispatch(preparedForDispatch); 
         processedMessage.setTimestamp(Instant.now()); 
  
         // Step 6: Save ProcessedMessage 
         processedMessageRepository.save(processedMessage); 
  
         // Step 7: Return ProcessedMessage 
         return processedMessage; 
     } 
  
     private String personalizeContent(DomainEntityMessageRequest messageRequest) { 
         String content = messageRequest.getContent(); 
         // Assuming metadata contains personalization data as key-value pairs 
         if (messageRequest.getMetadata() != null && messageRequest.getMetadata() instanceof java.util.Map) { 
             @SuppressWarnings("unchecked") 
             java.util.Map<String, Object> metadata = (java.util.Map<String, Object>) messageRequest.getMetadata(); 
             for (java.util.Map.Entry<String, Object> entry : metadata.entrySet()) { 
                 String placeholder = "{{" + entry.getKey() + "}}"; 
                 String value = entry.getValue().toString(); 
                 content = content.replace(placeholder, value); 
             } 
         } 
         return content; 
     } 
 } 
 