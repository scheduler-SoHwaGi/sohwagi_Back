package org.project.sohwagi.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.gpt.service.GPTService;
import org.project.sohwagi.schedule.dto.ScheduleRequest;
import org.project.sohwagi.schedule.dto.ScheduleResponse;
import org.project.sohwagi.schedule.dto.ScheduleTextRequest;
import org.project.sohwagi.schedule.entity.Schedule;
import org.project.sohwagi.schedule.repository.ScheduleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final GPTService gptService;

	private final ScheduleJpaRepository scheduleJpaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponse> getSchedules(Long userId) {
		List<Schedule> schedules = scheduleJpaRepository.findAllByUserId(userId);
		List<ScheduleResponse> scheduleResponses = new ArrayList<>();

		for (Schedule schedule : schedules) {
			scheduleResponses.add(new ScheduleResponse(schedule));
		}

		return scheduleResponses;
	}

	@Override
	@Transactional
	public void deleteSchedule(Long scheduleId, Long userId) {
		Schedule schedule = findSchedule(scheduleId);

		if(!schedule.getUserId().equals(userId)) {
			throw new RuntimeException("일정 작성자만 삭제할 수 있습니다.");
		}

		scheduleJpaRepository.delete(schedule);
	}

	@Override
	@Transactional
	public Long createSchedule(ScheduleTextRequest request, Long userId) throws JsonProcessingException {
		ScheduleRequest scheduleRequest = gptService.gptCall(request.getText());
		Schedule schedule = new Schedule(scheduleRequest, userId);
		Schedule savedSchedule = scheduleJpaRepository.save(schedule);

		return savedSchedule.getId();
	}

	private Schedule findSchedule(Long scheduleId) {
		return scheduleJpaRepository.findById(scheduleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 일정은 존재하지 않습니다."));
	}
}
