package ai.shreds.infrastructure.configurations;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.WriteConcern;
import com.mongodb.ReadConcern;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InfrastructureMongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoConnectionString;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabaseName;

    @Override
    public MongoClient reactiveMongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoConnectionString))
                .writeConcern(WriteConcern.MAJORITY)
                .readConcern(ReadConcern.MAJORITY)
                .build();
        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDatabaseName;
    }
}