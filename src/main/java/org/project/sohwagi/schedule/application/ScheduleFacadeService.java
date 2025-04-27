package org.project.sohwagi.schedule.application;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.schedule.application.cmd.ScheduleCommand;
import org.project.sohwagi.schedule.application.cmd.ScheduleCommand.GetSchedulesOnDateUseCase;
import org.project.sohwagi.schedule.application.domain.service.ScheduleService;
import org.project.sohwagi.schedule.application.info.ScheduleInfo;
import org.project.sohwagi.schedule.application.info.ScheduleInfo.ScheduleDetail;
import org.project.sohwagi.schedule.application.info.ScheduleInfo.ScheduleDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

  private final ScheduleService scheduleService;

  public ScheduleInfo.ScheduleCounts getScheduleCounts(ScheduleCommand.CountScheduleUseCase cmd) {
    Map<String, Integer> result = scheduleService.getScheduleCounts(cmd);

    return new ScheduleInfo.ScheduleCounts(result);
  }

  public ScheduleInfo.ScheduleDetails getSchedulesOnDate(GetSchedulesOnDateUseCase cmd) {
    List<ScheduleDetail> scheduleDetailList = scheduleService.getSchedulesOnDate(cmd);

    return new ScheduleInfo.ScheduleDetails(scheduleDetailList);
  }
}
