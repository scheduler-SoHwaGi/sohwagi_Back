package org.project.sohwagi.schedule.adapter.out.persistence;

import org.project.sohwagi.schedule.application.domain.model.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    List<Schedule> loadSchedulesByUserId(Long userId);

    Schedule loadScheduleById(Long scheduleId);

    void deleteSchedule(Schedule schedule);

    List<Schedule> findAllByUserIdAndYearAndMonth(Long userId, int year, int month);
}
