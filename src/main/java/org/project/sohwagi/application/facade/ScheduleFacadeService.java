package org.project.sohwagi.application.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.application.StatusGenerator;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCheckCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCountCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateByTextCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.SchedulesGetOnDate;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleCountInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleCountsInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailsInfo;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailInfo;
import org.project.sohwagi.common.exception.CustomException;
import org.project.sohwagi.common.exception.ErrorCode;
import org.project.sohwagi.domain.Schedule;
import org.project.sohwagi.infra.llm.LlmClient;
import org.project.sohwagi.infra.llm.LlmResult.ExtractedScheduleInformation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

  private final LlmClient llmClient;
  private final ScheduleService scheduleService;

  public ScheduleCountsInfo generateScheduleSummaries(ScheduleCountCommand cmd) {
    Map<String, List<Schedule>> result = scheduleService.getSchedulesGroupedByDate(cmd);

    List<ScheduleCountInfo> infos = result.entrySet().stream()
        .map(entry -> new ScheduleCountInfo(
            entry.getKey(),
            StatusGenerator.generateCounts(entry.getValue()),
            StatusGenerator.generateStatus(entry.getValue().size(), entry.getValue()))
        ).toList();

    return new ScheduleCountsInfo(infos);
  }

  public ScheduleDetailsInfo getSchedulesOnDate(SchedulesGetOnDate cmd) {
    List<ScheduleDetailInfo> scheduleDetailInfoList = scheduleService.getSchedulesOnDate(cmd);

    return new ScheduleDetailsInfo(scheduleDetailInfoList);
  }

  @Transactional
  public Long createScheduleByText(ScheduleCreateByTextCommand cmd) throws JsonProcessingException {
    ExtractedScheduleInformation scheduleInformation = llmClient.extractScheduleInformation(
        cmd.text());
    isScheduleTitleExists(scheduleInformation.title());

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

  private void isScheduleTitleExists(String title) {
    if (title == null) {
      throw new CustomException(ErrorCode.INVALID_SCHEDULE_TEXT_INPUT_VALUE);
    }
  }
}
