package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.exceptions.DomainExceptionScheduleNotFound;
import ai.shreds.domain.ports.DomainPortScheduleRepository;
import ai.shreds.infrastructure.repositories.jpa.ScheduleJpaRepository;
import ai.shreds.infrastructure.repositories.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InfrastructureRepositoryScheduleRepositoryImpl implements DomainPortScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public InfrastructureRepositoryScheduleRepositoryImpl(ScheduleJpaRepository scheduleJpaRepository, ScheduleMapper scheduleMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public DomainEntitySchedule findScheduleById(UUID scheduleId) throws DomainExceptionScheduleNotFound {
        return scheduleJpaRepository.findById(scheduleId)
                .map(scheduleEntity -> scheduleMapper.toDomainEntity(scheduleEntity))
                .orElseThrow(() -> new DomainExceptionScheduleNotFound("Schedule not found with id: " + scheduleId));
    }

    @Override
    public List<DomainEntitySchedule> findAllActiveSchedules() {
        return scheduleJpaRepository.findAllByStatus("active")
                .stream()
                .map(scheduleEntity -> scheduleMapper.toDomainEntity(scheduleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void save(DomainEntitySchedule schedule) {
        scheduleJpaRepository.save(scheduleMapper.toEntity(schedule));
    }
}