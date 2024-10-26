package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.exceptions.DomainExceptionScheduleNotFound;

import java.util.List;
import java.util.UUID;

public interface DomainPortScheduleRepository {

    DomainEntitySchedule findScheduleById(UUID scheduleId) throws DomainExceptionScheduleNotFound;

    List<DomainEntitySchedule> findAllActiveSchedules();

    void save(DomainEntitySchedule schedule);
}