package org.project.sohwagi.schedule.application.port.out;

import org.project.sohwagi.schedule.application.domain.model.Schedule;

public interface DeleteSchedulePort {

	void deleteSchedule(Schedule schedule);

}
