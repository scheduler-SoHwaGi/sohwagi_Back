package org.project.sohwagi.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleCountInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleCountsInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailsInfo;
import org.project.sohwagi.application.info.Status;
import org.project.sohwagi.domain.Schedule;

import java.util.List;

public class ScheduleResponse {

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class ScheduleDetailResponse {

    private Long scheduleId;

    private String title;

    private int month;

    private int day;

    private String dayOfWeek;

    private String time;

    public ScheduleDetailResponse(Schedule schedule) {
      this.scheduleId = schedule.getId();
      this.title = schedule.getTitle();
      this.month = schedule.getMonth();
      this.day = schedule.getDay();
      this.dayOfWeek = schedule.getDayOfWeek();
      this.time = schedule.getAmPm() + " " + schedule.getHour() + "시 " + String.format("%02d",
          schedule.getMinute()) + "분";
    }
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class WeekGroupedScheduleResponse {

    private String week;
    private String periodOfWeek;
    private List<ScheduleDetailResponse> schedules;
  }

  public record ScheduleCountResponse(String date, int counts, Status status) {

    public static ScheduleCountResponse from(ScheduleCountInfo info) {
      return new ScheduleCountResponse(info.date(), info.counts(), info.status());
    }
  }

  public record ScheduleCountsResponse(List<ScheduleCountResponse> scheduleCounts) {

    public static ScheduleCountsResponse from(ScheduleCountsInfo info) {
      return new ScheduleCountsResponse(
          info.scheduleCounts().stream().map(ScheduleCountResponse::from).toList()
      );
    }
  }

  public record ScheduleGetResponse(
      Long scheduleId,
      String title,
      String time,
      boolean checked
  ) {

    public static ScheduleGetResponse from(ScheduleDetailInfo info) {
      return new ScheduleGetResponse(
          info.scheduleId(),
          info.title(),
          info.amPm() + " " + info.hour() + "시 " + String.format("%02d", info.minute()) + "분",
          info.checked());
    }
  }

  public record ScheduleGetListResponse(
      List<ScheduleGetResponse> schedules
  ) {

    public static ScheduleGetListResponse from(ScheduleDetailsInfo info) {
      return new ScheduleGetListResponse(
          info.schedules().stream().map(ScheduleGetResponse::from).toList()
      );
    }
  }

}