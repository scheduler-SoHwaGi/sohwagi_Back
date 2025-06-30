package org.project.sohwagi.application.info;

import java.util.List;

public class ScheduleInfo {

  public record ScheduleCountInfo(String date, int counts, Status status) { }

  public record ScheduleDetailInfo(
      Long scheduleId,
      String title,
      String amPm,
      int hour,
      int minute,
      boolean checked
  ) {}

  public record ScheduleDetailsInfo(List<ScheduleDetailInfo> schedules) {}

  public record ScheduleCountsInfo(List<ScheduleCountInfo> scheduleCounts) {}
}
