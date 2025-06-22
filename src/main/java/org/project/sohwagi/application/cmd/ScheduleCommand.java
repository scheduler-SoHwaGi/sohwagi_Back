package org.project.sohwagi.application.cmd;

import java.time.LocalDate;

public class ScheduleCommand {

  public record ScheduleCountCommand(
      LocalDate start,
      LocalDate end,
      Long userId) {
    public static ScheduleCountCommand from(Long userId, LocalDate start, LocalDate end) {
      return new ScheduleCountCommand(start, end, userId);
    }
  }

  public record SchedulesGetOnDate(
      int year,
      int month,
      int day,
      Long userId
  ) {
    public static SchedulesGetOnDate from(int year, int month, int day, Long userId) {
      return new SchedulesGetOnDate(year, month, day, userId);
    }
  }

  public record ScheduleCreateByTextCommand(
      String text,
      Long userId
  ) { }

  public record ScheduleCreateCommand(
      String title,
      String date,
      Long userId
  ) { }

  public record ScheduleCheckCommand(Long userId) { }
}
