package org.project.sohwagi.schedule.application.cmd;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleCommand {

  public record CountScheduleUseCase(
      LocalDate start,
      LocalDate end) {
    public static CountScheduleUseCase from(LocalDate start, LocalDate end) {
      return new CountScheduleUseCase(start, end);
    }
  }

  public record GetSchedulesOnDateUseCase(
      int year,
      int month,
      int day,
      Long userId
  ) {
    public static GetSchedulesOnDateUseCase from(int year, int month, int day, Long userId) {
      return new GetSchedulesOnDateUseCase(year, month, day, userId);
    }
  }
}
