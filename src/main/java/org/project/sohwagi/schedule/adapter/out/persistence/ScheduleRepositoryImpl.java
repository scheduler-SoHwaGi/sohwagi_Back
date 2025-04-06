package org.project.sohwagi.schedule.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.schedule.application.domain.model.Schedule;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

	private final ScheduleJpaRepository scheduleJpaRepository;

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		long startTime = System.currentTimeMillis();

		Schedule savedSchedule = scheduleJpaRepository.save(schedule);

		log.info("db saved schedule in {} ms", System.currentTimeMillis() - startTime);
		return savedSchedule;
	}

	@Override
	public List<Schedule> loadSchedulesByUserId(Long userId) {
		return scheduleJpaRepository.findAllByUserIdOrderByMonthAscDayAsc(userId);
	}

	@Override
	public Schedule loadScheduleById(Long scheduleId) {
		return scheduleJpaRepository.findById(scheduleId)
			.orElseThrow(() -> new EntityNotFoundException("해당 스케줄은 존재하지 않습니다."));
	}

	@Override
	public void deleteSchedule(Schedule schedule) {
		scheduleJpaRepository.delete(schedule);
	}

	@Override
	public List<Schedule> findAllByUserIdAndYearAndMonth(Long userId, int year, int month) {
		return scheduleJpaRepository.findALlByUserIdAndYearAndMonth(userId, year, month);
    }
}
