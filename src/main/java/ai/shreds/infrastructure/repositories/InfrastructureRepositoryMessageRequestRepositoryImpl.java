package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.ports.DomainPortMessageRequestRepository;
import ai.shreds.domain.entities.DomainEntityMessageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import java.util.List;

@Repository
public class InfrastructureRepositoryMessageRequestRepositoryImpl implements DomainPortMessageRequestRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public InfrastructureRepositoryMessageRequestRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public DomainEntityMessageRequest findById(String id) {
        try {
            return mongoTemplate.findById(id, DomainEntityMessageRequest.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error finding message request by ID", e);
        }
    }

    @Override
    public void updateStatus(String id, String status) {
        try {
            Query query = new Query(Criteria.where("id").is(id));
            Update update = new Update().set("status", status);
            mongoTemplate.updateFirst(query, update, DomainEntityMessageRequest.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error updating message request status", e);
        }
    }

    @Override
    public List<DomainEntityMessageRequest> findAll() {
        try {
            return mongoTemplate.findAll(DomainEntityMessageRequest.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error finding all message requests", e);
        }
    }

}