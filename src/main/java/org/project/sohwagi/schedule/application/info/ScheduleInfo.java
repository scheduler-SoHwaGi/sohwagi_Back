package org.project.sohwagi.schedule.application.info;

import java.util.Map;

public class ScheduleInfo {

  public record ScheduleCounts(
      Map<String, Integer> scheduleCounts
  ) {

  }

}
