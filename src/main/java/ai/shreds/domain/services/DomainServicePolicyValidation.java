package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import ai.shreds.domain.entities.DomainEntityPolicy; 
 import ai.shreds.domain.ports.DomainPortPolicyRepository; 
  
 import java.util.List; 
 import java.util.regex.Pattern; 
  
 public class DomainServicePolicyValidation { 
     private final DomainPortPolicyRepository policyRepository; 
  
     public DomainServicePolicyValidation(DomainPortPolicyRepository policyRepository) { 
         this.policyRepository = policyRepository; 
     } 
  
     public Boolean validate(DomainEntityMessageRequest messageRequest) { 
         List<DomainEntityPolicy> policies = policyRepository.findAll(); 
  
         for (DomainEntityPolicy policy : policies) { 
             if (Boolean.TRUE.equals(policy.getIsActive())) { 
                 String criteria = policy.getCriteria(); 
                 // Assume criteria is a regular expression pattern 
                 Pattern pattern = Pattern.compile(criteria); 
                 if (pattern.matcher(messageRequest.getContent()).find()) { 
                     // Message content violates the policy criteria 
                     return false; 
                 } 
             } 
         } 
         // Message passes all active policies 
         return true; 
     } 
 } 
 