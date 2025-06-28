package org.project.sohwagi.domain;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    List<Schedule> loadSchedulesByUserId(Long userId);

    Schedule loadScheduleById(Long scheduleId);

    void deleteSchedule(Schedule schedule);

    List<Schedule> findAllByUserIdAndYearAndMonth(Long userId, int year, int month);

    List<Object[]> findCountByDateBetween(LocalDate start, LocalDate end);

    long countByYearAndMonthAndDay(Long userId, int year, int month, int day);

    List<Schedule> findAllByUserIdAndYearAndMonthAndDay(Long userId, int year, int month, int day);

    List<Schedule> findTodaySchedules(LocalDate today);

    Schedule findScheduleById(Long scheduleId);

    List<Schedule> findSchedulesByUserIdAndYearAndMonthAndDay(
        Long userId,
        int year,
        int month,
        int day
    );
}
