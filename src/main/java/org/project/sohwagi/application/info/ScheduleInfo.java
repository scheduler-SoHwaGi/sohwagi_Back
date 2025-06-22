package org.project.sohwagi.application.info;

import java.util.List;
import java.util.Map;

public class ScheduleInfo {

  public record ScheduleCountsInfo(
      Map<String, Integer> scheduleCounts
  ) {

  }

  public record ScheduleDetailInfo(
      Long scheduleId,
      String title,
      String amPm,
      int hour,
      int minute
  ) {}

  public record ScheduleDetailsInfo(
      List<ScheduleDetailInfo> schedules
  ) {}



}
