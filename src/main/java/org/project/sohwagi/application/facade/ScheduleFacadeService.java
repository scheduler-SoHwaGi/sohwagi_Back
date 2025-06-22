package org.project.sohwagi.application.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCheckCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCountCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateByTextCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.SchedulesGetOnDate;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.info.ScheduleInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetail;
import org.project.sohwagi.infra.llm.LlmClient;
import org.project.sohwagi.infra.llm.LlmResult.ExtractedScheduleInformation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

  private final LlmClient llmClient;
  private final ScheduleService scheduleService;

  public ScheduleInfo.ScheduleCounts getScheduleCounts(ScheduleCountCommand cmd) {
    Map<String, Integer> result = scheduleService.getScheduleCounts(cmd);

    return new ScheduleInfo.ScheduleCounts(result);
  }

  public ScheduleInfo.ScheduleDetails getSchedulesOnDate(SchedulesGetOnDate cmd) {
    List<ScheduleDetail> scheduleDetailList = scheduleService.getSchedulesOnDate(cmd);

    return new ScheduleInfo.ScheduleDetails(scheduleDetailList);
  }

  @Transactional
  public Long createScheduleByText(ScheduleCreateByTextCommand cmd) throws JsonProcessingException {
    ExtractedScheduleInformation scheduleInformation = llmClient.extractScheduleInformation(
        cmd.text());

    return scheduleService.createScheduleByText(
        new ScheduleCreateCommand(
            scheduleInformation.title(),
            scheduleInformation.date(),
            cmd.userId()
        )
    );
  }

  @Transactional
  public void checkSchedule(ScheduleCheckCommand cmd) {
    scheduleService.checkSchedule(cmd);
  }
}
