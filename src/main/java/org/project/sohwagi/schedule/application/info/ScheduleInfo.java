package org.project.sohwagi.schedule.application.info;

import java.util.List;
import java.util.Map;

public class ScheduleInfo {

  public record ScheduleCounts(
      Map<String, Integer> scheduleCounts
  ) {

  }

  public record ScheduleDetail(
      Long scheduleId,
      String title,
      String amPm,
      int hour,
      int minute
  ) {}

  public record ScheduleDetails(
      List<ScheduleDetail> schedules
  ) {}

}
