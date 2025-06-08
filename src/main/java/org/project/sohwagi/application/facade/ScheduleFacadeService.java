package org.project.sohwagi.application.facade;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.application.cmd.ScheduleCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.GetSchedulesOnDateUseCase;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.info.ScheduleInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetail;
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
