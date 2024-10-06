package org.project.sohwagi.domain.schedule.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;
import org.project.sohwagi.domain.schedule.dto.ScheduleResponse;
import org.project.sohwagi.domain.schedule.entity.Schedule;
import org.project.sohwagi.domain.schedule.repository.ScheduleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleJpaRepository scheduleJpaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponse> getSchedules() {
		List<Schedule> schedules = scheduleJpaRepository.findAll();
		List<ScheduleResponse> scheduleResponses = new ArrayList<>();

		for (Schedule schedule : schedules) {
			scheduleResponses.add(new ScheduleResponse(schedule));
		}

		return scheduleResponses;
	}

	@Override
	@Transactional
	public void deleteSchedule(Long scheduleId) {
		Schedule schedule = findSchedule(scheduleId);
		scheduleJpaRepository.delete(schedule);
	}

	@Override
	@Transactional
	public Long createSchedule(ScheduleRequest request) {
		Schedule schedule = new Schedule(request);
		Schedule savedSchedule = scheduleJpaRepository.save(schedule);

		return savedSchedule.getId();
	}

	private Schedule findSchedule(Long scheduleId) {
		return scheduleJpaRepository.findById(scheduleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 일정은 존재하지 않습니다."));
	}
}
