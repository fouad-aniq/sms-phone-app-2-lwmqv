package ai.shreds.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.beans.factory.annotation.Value;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

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
    public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String mongoUri) {
        // Create a MongoClient using the mongoUri from external configuration
        return MongoClients.create(mongoUri);
    }
}