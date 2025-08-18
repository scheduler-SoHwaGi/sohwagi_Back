package org.project.sohwagi.application.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCheckCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateCommand;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.common.exception.CustomException;
import org.project.sohwagi.common.exception.ErrorCode;
import org.project.sohwagi.domain.ScheduleRepository;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCountCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.SchedulesGetOnDate;
import org.project.sohwagi.domain.Schedule;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailInfo;
import org.project.sohwagi.application.cmd.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Service
@RequiredArgsConstructor
public class ScheduleService
    implements DeleteScheduleUseCase {

  private final ScheduleRepository scheduleRepository;

  @Transactional
  public Long createScheduleByText(ScheduleCreateCommand command) {
    log.info("Create schedule by text 시작");
    Schedule newSchedule = new Schedule(
        command.title(),
        command.userId(),
        command.year(),
        command.month(),
        command.day(),
        command.dayOfWeek(),
        command.ampm(),
        command.hour(),
        command.minute()
    );
    Schedule savedSchedule = scheduleRepository.saveSchedule(newSchedule);
    return savedSchedule.getId();
  }


  @Override
  @Transactional
  public void deleteSchedule(DeleteScheduleCommand command) {
    Schedule schedule = scheduleRepository.loadScheduleById(command.scheduleId());

    scheduleRepository.deleteSchedule(schedule);
  }

  @Transactional
  public void deleteScheduleByUserRevoke(Long userId) {
    List<Schedule> schedules = scheduleRepository.loadSchedulesByUserId(userId);

    for (Schedule schedule : schedules) {
      scheduleRepository.deleteSchedule(schedule);
    }
  }

  public Map<String, List<Schedule>> getSchedulesGroupedByDate(ScheduleCountCommand cmd) {

    long days = ChronoUnit.DAYS.between(cmd.start(), cmd.end()) + 1;
    return Stream.iterate(cmd.start(), date -> date.plusDays(1))
        .limit(days)
        .collect(Collectors.toMap(
            LocalDate::toString,
            date -> scheduleRepository.findSchedulesByUserIdAndYearAndMonthAndDay(
                cmd.userId(),
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
            )
        ));
  }

  public List<ScheduleDetailInfo> getSchedulesOnDate(SchedulesGetOnDate cmd) {
    List<Schedule> schedules = scheduleRepository.findAllByUserIdAndYearAndMonthAndDay(cmd.userId(),
        cmd.year(),
        cmd.month(), cmd.day());

    return schedules.stream().map(
            s -> new ScheduleDetailInfo(
                s.getId(),
                s.getTitle(),
                s.getAmPm(),
                s.getHour(),
                s.getMinute(),
                s.getChecked()
            )
        )
        .toList();
  }

  public List<Schedule> findTodaySchedules(LocalDate today) {
    return scheduleRepository.findTodaySchedules(today) ;
  }

  public void checkSchedule(ScheduleCheckCommand cmd) {
    Schedule schedule = scheduleRepository.findScheduleById(cmd.userId());

    schedule.checkSchedule(schedule.getChecked());
  }
}