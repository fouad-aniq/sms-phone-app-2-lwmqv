package ai.shreds.domain.ports;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import ai.shreds.domain.entities.DomainEntitySchedule;

public interface DomainPortScheduleCache {

    List<DomainEntitySchedule> getAllSchedules();

    List<DomainEntitySchedule> getSchedulesDueBy(Timestamp time);

    DomainEntitySchedule getScheduleById(UUID scheduleId);

    void saveSchedule(DomainEntitySchedule schedule);

    void deleteSchedule(UUID scheduleId);
}