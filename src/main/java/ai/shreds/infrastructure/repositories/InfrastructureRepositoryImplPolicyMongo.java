package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityPolicy; 
 import ai.shreds.domain.ports.DomainPortPolicyRepository; 
 import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException; 
 import ai.shreds.infrastructure.repositories.mongo.PolicyMongoRepository; 
 import ai.shreds.infrastructure.repositories.mongo.documents.PolicyDocument; 
 import ai.shreds.infrastructure.repositories.mongo.mappers.PolicyMapper; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.dao.DataAccessException; 
 import org.springframework.stereotype.Repository; 
  
 import java.util.List; 
 import java.util.Optional; 
  
 @Repository 
 public class InfrastructureRepositoryImplPolicyMongo implements DomainPortPolicyRepository { 
  
     private final PolicyMongoRepository policyMongoRepository; 
     private final PolicyMapper policyMapper; 
  
     @Autowired 
     public InfrastructureRepositoryImplPolicyMongo(PolicyMongoRepository policyMongoRepository, 
                                                    PolicyMapper policyMapper) { 
         this.policyMongoRepository = policyMongoRepository; 
         this.policyMapper = policyMapper; 
     } 
  
     @Override 
     public List<DomainEntityPolicy> findAll() { 
         try { 
             List<PolicyDocument> policyDocuments = policyMongoRepository.findAll(); 
             return policyMapper.toDomainEntities(policyDocuments); 
         } catch (DataAccessException e) { 
             throw new InfrastructureExceptionDataAccessException("Failed to fetch all policies", e); 
         } 
     } 
  
     @Override 
     public DomainEntityPolicy findById(String id) { 
         try { 
             Optional<PolicyDocument> optionalPolicy = policyMongoRepository.findById(id); 
             if (optionalPolicy.isPresent()) { 
                 return policyMapper.toDomainEntity(optionalPolicy.get()); 
             } else { 
                 throw new InfrastructureExceptionDataAccessException("Policy not found with id: " + id); 
             } 
         } catch (DataAccessException e) { 
             throw new InfrastructureExceptionDataAccessException("Failed to fetch policy with id: " + id, e); 
         } 
     } 
 } 
 