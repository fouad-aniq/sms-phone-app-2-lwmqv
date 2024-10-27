package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.domain.ports.DomainPortDeliveryDetailsRepository;
import ai.shreds.infrastructure.entities.DeliveryDetailsEntity;
import ai.shreds.application.mappers.DeliveryDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public class InfrastructureRepositoryImplDeliveryDetails implements DomainPortDeliveryDetailsRepository {

    @Autowired
    private MongoRepository<DeliveryDetailsEntity, String> deliveryDetailsMongoRepository;

    @Autowired
    private DeliveryDetailsMapper deliveryDetailsMapper;

    @Override
    public void save(DomainEntityDeliveryDetails deliveryDetails) {
        DeliveryDetailsEntity entity = deliveryDetailsMapper.toEntity(deliveryDetails);
        deliveryDetailsMongoRepository.save(entity);
    }

    @Override
    public DomainEntityDeliveryDetails findByMessageId(String messageId) {
        return deliveryDetailsMongoRepository.findById(messageId).map(deliveryDetailsMapper::toDomain).orElse(null);
    }
}