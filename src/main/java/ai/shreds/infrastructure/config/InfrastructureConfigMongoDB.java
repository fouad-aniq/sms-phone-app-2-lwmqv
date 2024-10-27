package ai.shreds.infrastructure.config; 
  
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.context.annotation.Bean; 
 import com.mongodb.ConnectionString; 
 import com.mongodb.MongoClientSettings; 
 import com.mongodb.client.MongoClient; 
 import com.mongodb.client.MongoClients; 
 import org.springframework.data.mongodb.core.MongoTemplate; 
 import org.springframework.data.mongodb.core.MongoDatabaseFactory; 
 import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory; 
  
 @Configuration 
 public class InfrastructureConfigMongoDB { 
  
     @Value("${mongodb.uri}") 
     private String mongoUri; 
  
     public void configureMongoDB() { 
         // Additional MongoDB configuration can be added here if necessary. 
     } 
  
     @Bean 
     public MongoClient mongoClient() { 
         configureMongoDB(); // Ensuring any custom configurations are applied. 
         ConnectionString connectionString = new ConnectionString(mongoUri); 
         MongoClientSettings mongoClientSettings = MongoClientSettings.builder() 
             .applyConnectionString(connectionString) 
             .build(); 
         return MongoClients.create(mongoClientSettings); 
     } 
  
     @Bean 
     public MongoDatabaseFactory mongoDatabaseFactory() { 
         return new SimpleMongoClientDatabaseFactory(mongoClient()); 
     } 
  
     @Bean 
     public MongoTemplate mongoTemplate() { 
         return new MongoTemplate(mongoDatabaseFactory()); 
     } 
 } 
 