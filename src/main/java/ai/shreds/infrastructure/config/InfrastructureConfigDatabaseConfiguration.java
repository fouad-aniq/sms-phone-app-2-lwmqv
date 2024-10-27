package ai.shreds.infrastructure.config; 
  
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.context.annotation.Bean; 
 import org.springframework.boot.autoconfigure.mongo.MongoProperties; 
 import org.springframework.beans.factory.annotation.Value; 
 import com.mongodb.reactivestreams.client.MongoClient; 
 import com.mongodb.reactivestreams.client.MongoClients; 
  
 @Configuration 
 public class InfrastructureConfigDatabaseConfiguration { 
  
     @Bean 
     public MongoProperties mongoConfig(@Value("${spring.data.mongodb.database}") String databaseName) { 
         MongoProperties mongoProperties = new MongoProperties(); 
         // Set the MongoDB database name from external configuration 
         mongoProperties.setDatabase(databaseName); 
         // Additional properties will be automatically bound from application properties 
         return mongoProperties; 
     } 
  
     @Bean 
     public MongoClient reactiveMongoClient(@Value("${spring.data.mongodb.uri}") String mongoUri) { 
         // Create a ReactiveMongoClient using the mongoUri from external configuration 
         return MongoClients.create(mongoUri); 
     } 
  
 }