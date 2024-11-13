package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.ports.DomainScheduleRepositoryPort;
import ai.shreds.infrastructure.entities.InfrastructureScheduleEntity;
import ai.shreds.infrastructure.mappers.InfrastructureScheduleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InfrastructureScheduleRepositoryImpl implements DomainScheduleRepositoryPort {

    private final JpaScheduleRepository jpaScheduleRepository;
    private final InfrastructureScheduleMapper scheduleMapper;

    public InfrastructureScheduleRepositoryImpl(JpaScheduleRepository jpaScheduleRepository,
                                                InfrastructureScheduleMapper scheduleMapper) {
        this.jpaScheduleRepository = jpaScheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public void save(DomainScheduleEntity schedule) {
        InfrastructureScheduleEntity entity = scheduleMapper.toDatabaseEntity(schedule);
        jpaScheduleRepository.save(entity);
    }

    @Override
    public Optional<DomainScheduleEntity> findById(UUID scheduleId) {
        Optional<InfrastructureScheduleEntity> optionalEntity = jpaScheduleRepository.findById(scheduleId);
        return optionalEntity.map(scheduleMapper::toDomainEntityFromDatabase);
    }

    @Override
    public void deleteById(UUID scheduleId) {
        jpaScheduleRepository.deleteById(scheduleId);
    }

    @Override
    public boolean existsById(UUID scheduleId) {
        return jpaScheduleRepository.existsById(scheduleId);
    }
}

interface JpaScheduleRepository extends JpaRepository<InfrastructureScheduleEntity, UUID> {
    // JpaRepository provides the basic CRUD operations
}