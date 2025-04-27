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
}
