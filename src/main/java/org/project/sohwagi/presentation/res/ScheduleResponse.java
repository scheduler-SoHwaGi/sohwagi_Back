package org.project.sohwagi.presentation.res;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.domain.Schedule;

import java.util.List;
import org.project.sohwagi.application.info.ScheduleInfo;

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

  public record V1_GetScheduleCount(Map<String, Integer> scheduleCounts) {

    public static V1_GetScheduleCount from(ScheduleInfo.ScheduleCounts info) {
      return new V1_GetScheduleCount(info.scheduleCounts());
    }
  }

  public record V1_Get(
      Long scheduleId,
      String title,
      String time
  ) {

    public static V1_Get from(ScheduleInfo.ScheduleDetail info) {
      return new V1_Get(info.scheduleId(), info.title(),
          info.amPm() + " " + info.hour() + "시 " + String.format("%02d",
              info.minute()) + "분");
    }
  }

  public record V1_GetList(
      List<V1_Get> schedules
  ) {

    public static V1_GetList from(ScheduleInfo.ScheduleDetails info) {
      return new V1_GetList(
          info.schedules().stream().map(V1_Get::from).toList()
      );
    }
  }

}