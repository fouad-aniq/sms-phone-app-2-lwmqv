package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.ports.DomainScheduleRepositoryPort;
import ai.shreds.infrastructure.entities.InfrastructureScheduleEntity;
import ai.shreds.infrastructure.exceptions.InfrastructureDataAccessException;
import ai.shreds.infrastructure.mappers.InfrastructureScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class InfrastructureScheduleRepositoryImpl implements DomainScheduleRepositoryPort {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private InfrastructureScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public void save(DomainScheduleEntity schedule) {
        try {
            InfrastructureScheduleEntity infrastructureScheduleEntity = scheduleMapper.toDatabaseEntity(schedule);
            scheduleRepository.save(infrastructureScheduleEntity);
        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint violation for duplicates
            throw new InfrastructureDataAccessException("Duplicate schedule detected.", e);
        } catch (OptimisticLockingFailureException e) {
            // Handle optimistic locking failure due to concurrent modifications
            throw new InfrastructureDataAccessException("Optimistic locking failure occurred while saving the schedule.", e);
        } catch (DataAccessException e) {
            // Handle other data access exceptions
            throw new InfrastructureDataAccessException("Data access error occurred while saving the schedule.", e);
        }
    }

    @Override
    public DomainScheduleEntity findById(UUID scheduleId) {
        try {
            Optional<InfrastructureScheduleEntity> optionalEntity = scheduleRepository.findById(scheduleId);
            if (optionalEntity.isPresent()) {
                return scheduleMapper.toDomainEntityFromDatabase(optionalEntity.get());
            } else {
                return null; // or throw an exception if not found
            }
        } catch (DataAccessException e) {
            throw new InfrastructureDataAccessException("Data access error occurred while finding the schedule.", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(UUID scheduleId) {
        try {
            scheduleRepository.deleteById(scheduleId);
        } catch (DataAccessException e) {
            throw new InfrastructureDataAccessException("Data access error occurred while deleting the schedule.", e);
        }
    }

    @Override
    public boolean existsById(UUID scheduleId) {
        try {
            return scheduleRepository.existsById(scheduleId);
        } catch (DataAccessException e) {
            throw new InfrastructureDataAccessException("Data access error occurred while checking if schedule exists.", e);
        }
    }
}
