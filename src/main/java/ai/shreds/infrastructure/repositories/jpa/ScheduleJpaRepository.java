package ai.shreds.infrastructure.repositories.jpa;

import ai.shreds.domain.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, UUID> {

    Optional<ScheduleEntity> findById(UUID scheduleId);

    List<ScheduleEntity> findAllByStatus(String status);
}