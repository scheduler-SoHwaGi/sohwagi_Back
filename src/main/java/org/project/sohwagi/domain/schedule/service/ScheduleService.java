package org.project.sohwagi.domain.schedule.service;

import java.util.List;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;
import org.project.sohwagi.domain.schedule.dto.ScheduleResponse;

public interface ScheduleService {

	List<ScheduleResponse> getSchedules();

	void deleteSchedule(Long scheduleId);

	Long createSchedule(ScheduleRequest request);
}
