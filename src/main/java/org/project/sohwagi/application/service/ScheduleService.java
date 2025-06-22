package org.project.sohwagi.application.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
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
import org.project.sohwagi.domain.ScheduleRepository;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCountCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.SchedulesGetOnDate;
import org.project.sohwagi.domain.Schedule;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetail;
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

  public Long createScheduleByText(ScheduleCreateCommand command) {
    log.info("Create schedule by text 시작");

    Schedule schedule = parseDateString(command);

    Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);

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

  private Schedule parseDateString(ScheduleCreateCommand command) {
    log.info(command.date());
    long startTime = System.currentTimeMillis();

    Pattern pattern = Pattern.compile(
        "(\\d{4})년 (\\d{1,2})월 (\\d{1,2})일 (\\S+) (오전|오후) (\\d{1,2})시 (\\d{2})분");
    Matcher matcher = pattern.matcher(command.date());

    if (matcher.matches()) {
      int year = Integer.parseInt(matcher.group(1));
      int month = Integer.parseInt(matcher.group(2));
      int day = Integer.parseInt(matcher.group(3));
      String dayOfWeek = matcher.group(4);
      String amPm = matcher.group(5);
      int hour = Integer.parseInt(matcher.group(6));
      int minute = Integer.parseInt(matcher.group(7));

      log.info("parseDateString proceeds in {} ms", System.currentTimeMillis() - startTime);
      return new Schedule(command.title(), command.userId(), year, month, day, dayOfWeek, amPm, hour,
          minute);
    } else {
      throw new IllegalArgumentException("Invalid date format: " + command.date());
    }
  }

  private int convertTo24Hour(String amPm, int hour) {
    if ("오전".equals(amPm)) {
      return hour == 12 ? 0 : hour;
    } else {
      return hour == 12 ? 12 : hour + 12;
    }
  }

  public Map<String, Integer> getScheduleCounts(ScheduleCountCommand cmd) {

    long days = ChronoUnit.DAYS.between(cmd.start(), cmd.end()) + 1;
    return Stream.iterate(cmd.start(), date -> date.plusDays(1))
        .limit(days)
        .collect(Collectors.toMap(
            LocalDate::toString,
            date -> (int) scheduleRepository.countByYearAndMonthAndDay(
                cmd.userId(),
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
            ),
            (a, b) -> b,
            LinkedHashMap::new
        ));
  }

  public List<ScheduleDetail> getSchedulesOnDate(SchedulesGetOnDate cmd) {
    List<Schedule> schedules = scheduleRepository.findAllByUserIdAndYearAndMonthAndDay(cmd.userId(),
        cmd.year(),
        cmd.month(), cmd.day());

    return schedules.stream().map(
            s -> new ScheduleDetail(s.getId(), s.getTitle(), s.getAmPm(), s.getHour(), s.getMinute()))
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