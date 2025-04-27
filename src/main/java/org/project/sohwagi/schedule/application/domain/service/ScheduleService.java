package org.project.sohwagi.schedule.application.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleRequest;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.adapter.out.persistence.ScheduleRepository;
import org.project.sohwagi.schedule.adapter.out.persistence.ScheduleRepositoryImpl;
import org.project.sohwagi.schedule.application.cmd.ScheduleCommand.GetSchedulesOnDateUseCase;
import org.project.sohwagi.schedule.application.domain.model.Schedule;
import org.project.sohwagi.schedule.application.domain.model.YearWeekKey;
import org.project.sohwagi.schedule.application.info.ScheduleInfo;
import org.project.sohwagi.schedule.application.info.ScheduleInfo.ScheduleDetail;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;
import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;
import org.project.sohwagi.schedule.application.port.in.usecase.CreateScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.GetScheduleUseCase;
import org.project.sohwagi.schedule.application.port.out.CallGptPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Service
@RequiredArgsConstructor
public class ScheduleService
    implements CreateScheduleUseCase, GetScheduleUseCase, DeleteScheduleUseCase {

  private final CallGptPort callGptPort;
  private final ScheduleRepository scheduleRepository;

  @Override
  @Transactional
  public Long createScheduleByText(CreateScheduleByTextCommand command)
      throws JsonProcessingException {
    log.info("Create schedule by text 시작");

    ScheduleRequest scheduleRequest = callGptPort.callGptForTextSchedule(command.text());

    Schedule schedule = parseDateString(scheduleRequest, command.userId());

    Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);

    return savedSchedule.getId();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ScheduleResponse.WeekGroupedScheduleResponse> getScheduleList(
      GetScheduleListQuery query) {
    List<Schedule> schedules = scheduleRepository.findAllByUserIdAndYearAndMonth(query.userId(),
        query.year(), query.month());

    Map<YearWeekKey, List<Schedule>> grouped = schedules.stream()
        .collect(
            Collectors.groupingBy(s -> YearWeekKey.from(s.getYear(), s.getMonth(), s.getDay())));

    return grouped.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> new ScheduleResponse.WeekGroupedScheduleResponse(
            entry.getKey().toLabel(),
            entry.getKey().toPeriodString(),
            entry.getValue().stream()
                .sorted(Comparator.comparing(s -> LocalDateTime.of(
                    LocalDate.of(query.year(), query.month(), s.getDay()),
                    LocalTime.of(convertTo24Hour(s.getAmPm(), s.getHour()), s.getMinute())
                )))
                .map(ScheduleResponse.ScheduleDetailResponse::new)
                .toList()
        ))
        .toList();
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

  private Schedule parseDateString(ScheduleRequest request, Long userId) {
    log.info(request.getDate());
    long startTime = System.currentTimeMillis();

    Pattern pattern = Pattern.compile(
        "(\\d{4})년 (\\d{1,2})월 (\\d{1,2})일 (\\S+) (오전|오후) (\\d{1,2})시 (\\d{2})분");
    Matcher matcher = pattern.matcher(request.getDate());

    if (matcher.matches()) {
      int year = Integer.parseInt(matcher.group(1));
      int month = Integer.parseInt(matcher.group(2));
      int day = Integer.parseInt(matcher.group(3));
      String dayOfWeek = matcher.group(4);
      String amPm = matcher.group(5);
      int hour = Integer.parseInt(matcher.group(6));
      int minute = Integer.parseInt(matcher.group(7));

      log.info("parseDateString proceeds in {} ms", System.currentTimeMillis() - startTime);
      return new Schedule(request.getTitle(), userId, year, month, day, dayOfWeek, amPm, hour,
          minute);
    } else {
      throw new IllegalArgumentException("Invalid date format: " + request.getDate());
    }
  }

  private int convertTo24Hour(String amPm, int hour) {
    if ("오전".equals(amPm)) {
      return hour == 12 ? 0 : hour;
    } else {
      return hour == 12 ? 12 : hour + 12;
    }
  }

  public Map<String, Integer> getScheduleCounts(LocalDate start, LocalDate end) {

    long days = ChronoUnit.DAYS.between(start, end) + 1;
    return Stream.iterate(start, date -> date.plusDays(1))
        .limit(days)
        .collect(Collectors.toMap(
            LocalDate::toString,
            date -> (int) scheduleRepository.countByYearAndMonthAndDay(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
            ),
            (a, b) -> b,
            LinkedHashMap::new
        ));
  }

  public List<ScheduleDetail> getSchedulesOnDate(GetSchedulesOnDateUseCase cmd) {
    List<Schedule> schedules = scheduleRepository.findAllByUserIdAndYearAndMonthAndDay(cmd.userId(),
        cmd.year(),
        cmd.month(), cmd.day());

    return schedules.stream().map(
            s -> new ScheduleDetail(s.getId(), s.getTitle(), s.getAmPm(), s.getHour(), s.getMinute()))
        .toList();
  }
}
