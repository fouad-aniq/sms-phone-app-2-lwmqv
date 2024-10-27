package ai.shreds.infrastructure.config; 
  
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.context.annotation.Bean; 
 import com.mongodb.client.MongoClient; 
 import com.mongodb.client.MongoClients; 
 import org.springframework.data.mongodb.core.MongoTemplate; 
  
 @Configuration 
 public class InfrastructureConfigMongoDBConfig { 
  
     @Value("${spring.data.mongodb.uri}") 
     private String mongoUri; 
  
     @Value("${spring.data.mongodb.database}") 
     private String databaseName; 
  
     public void mongoDbConfig() { 
         // Implement any custom MongoDB configurations if necessary 
     } 
  
     @Bean 
     public MongoClient mongoClient() { 
         return MongoClients.create(mongoUri); 
     } 
  
     @Bean 
     public MongoTemplate mongoTemplate() { 
         return new MongoTemplate(mongoClient(), databaseName); 
     } 
 } 
 