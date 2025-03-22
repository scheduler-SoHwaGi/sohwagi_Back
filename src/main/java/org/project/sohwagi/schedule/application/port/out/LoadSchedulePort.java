package org.project.sohwagi.schedule.application.port.out;

import java.util.List;
import org.project.sohwagi.schedule.application.domain.model.Schedule;

public interface LoadSchedulePort {

	List<Schedule> loadSchedulesByUserId(Long userId);

	Schedule loadScheduleById(Long scheduleId);
}
