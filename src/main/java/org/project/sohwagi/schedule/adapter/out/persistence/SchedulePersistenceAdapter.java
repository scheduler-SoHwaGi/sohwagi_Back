package org.project.sohwagi.schedule.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.schedule.adapter.out.persistence.respository.ScheduleJpaRepository;
import org.project.sohwagi.schedule.application.domain.model.Schedule;
import org.project.sohwagi.schedule.application.port.out.DeleteSchedulePort;
import org.project.sohwagi.schedule.application.port.out.LoadSchedulePort;
import org.project.sohwagi.schedule.application.port.out.SaveSchedulePort;

@PersistenceAdapter
@RequiredArgsConstructor
public class SchedulePersistenceAdapter implements SaveSchedulePort, LoadSchedulePort,
	DeleteSchedulePort {

	private final ScheduleJpaRepository scheduleJpaRepository;

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return scheduleJpaRepository.save(schedule);
	}

	@Override
	public List<Schedule> loadSchedulesByUserId(Long userId) {
		return scheduleJpaRepository.findAllByUserId(userId);
	}

	@Override
	public Schedule loadScheduleById(Long scheduleId) {
		return scheduleJpaRepository.findById(scheduleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 스케줄은 존재하지 않습니다."));
	}

	@Override
	public void deleteSchedule(Schedule schedule) {
		scheduleJpaRepository.delete(schedule);
	}
}
