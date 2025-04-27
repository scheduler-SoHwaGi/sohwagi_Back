package org.project.sohwagi.schedule.adapter.out.persistence;

import java.util.List;
import org.project.sohwagi.schedule.application.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findAllByUserIdOrderByMonthAscDayAsc(Long userId);

	List<Schedule> findALlByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);

	long countByYearAndMonthAndDay(int year, int month, int day);

}
