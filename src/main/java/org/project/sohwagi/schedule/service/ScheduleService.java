package org.project.sohwagi.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.project.sohwagi.schedule.dto.ScheduleResponse;
import org.project.sohwagi.schedule.dto.ScheduleTextRequest;

public interface ScheduleService {

	List<ScheduleResponse> getSchedules(Long userId);

	void deleteSchedule(Long scheduleId, Long userId);

	Long createSchedule(ScheduleTextRequest request, Long userId) throws JsonProcessingException;
}
