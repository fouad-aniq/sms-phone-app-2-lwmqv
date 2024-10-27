package ai.shreds.infrastructure.configurations; 
  
 import com.mongodb.reactivestreams.client.MongoClient; 
 import com.mongodb.reactivestreams.client.MongoClients; 
 import com.mongodb.WriteConcern; 
 import com.mongodb.ReadConcern; 
 import com.mongodb.ConnectionString; 
 import com.mongodb.MongoClientSettings; 
  
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.context.annotation.Bean; 
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.data.mongodb.core.ReactiveMongoTemplate; 
  
 import lombok.extern.slf4j.Slf4j; 
  
 @Configuration 
 @Slf4j 
 public class InfrastructureMongoConfig { 
  
     @Value("${spring.data.mongodb.uri}") 
     private String mongoConnectionString; 
  
     @Value("${spring.data.mongodb.database}") 
     private String mongoDatabaseName; 
  
     public void configureMongoDb() { 
         log.info("Configuring MongoDB settings."); 
         // Additional configurations can be added here if needed to align with database settings. 
     } 
  
     @Bean 
     public MongoClient reactiveMongoClient() { 
         MongoClientSettings settings = MongoClientSettings.builder() 
                 .applyConnectionString(new ConnectionString(mongoConnectionString)) 
                 .writeConcern(WriteConcern.MAJORITY) 
                 .readConcern(ReadConcern.MAJORITY) 
                 .build(); 
         return MongoClients.create(settings); 
     } 
  
     @Bean 
     public ReactiveMongoTemplate reactiveMongoTemplate() { 
         return new ReactiveMongoTemplate(reactiveMongoClient(), mongoDatabaseName); 
     } 
 } 
 