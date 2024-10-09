package org.project.sohwagi.domain.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;
import org.project.sohwagi.domain.schedule.dto.ScheduleResponse;
import org.project.sohwagi.domain.schedule.dto.ScheduleTextRequest;

public interface ScheduleService {

	List<ScheduleResponse> getSchedules(Long userId);

	void deleteSchedule(Long scheduleId, Long userId);

	Long createSchedule(ScheduleTextRequest request, Long userId) throws JsonProcessingException;
}
