package org.project.sohwagi.infra.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.project.sohwagi.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findAllByUserIdOrderByMonthAscDayAsc(Long userId);

  List<Schedule> findALlByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);

  long countByUserIdAndYearAndMonthAndDay(Long userId, int year, int month, int day);

  List<Schedule> findAllByUserIdAndYearAndMonthAndDayOrderByAmPmAscHourAscMinuteAsc(
      Long userId,
      int year,
      int month,
      int day
  );

  List<Schedule> findAllByYearAndMonthAndDay(int year, int month, int day);

  List<Schedule> findAllByUserIdAndYearAndMonthAndDay(
      Long userId,
      Integer year,
      Integer month,
      int day
  );
}
