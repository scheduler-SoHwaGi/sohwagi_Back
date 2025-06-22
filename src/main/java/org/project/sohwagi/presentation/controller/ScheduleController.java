package org.project.sohwagi.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCheckCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCountCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateByTextCommand;
import org.project.sohwagi.application.cmd.ScheduleCommand.SchedulesGetOnDate;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleCountsInfo;
import org.project.sohwagi.application.info.ScheduleInfo.ScheduleDetailsInfo;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.presentation.req.ScheduleRequest.ScheduleCreateByTextRequest;
import org.project.sohwagi.presentation.res.ScheduleResponse;
import org.project.sohwagi.presentation.res.ScheduleResponse.ScheduleGetListResponse;
import org.project.sohwagi.presentation.res.ScheduleResponse.V1_GetScheduleCount;
import org.project.sohwagi.application.facade.ScheduleFacadeService;
import org.project.sohwagi.application.cmd.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.domain.UserDetails;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final DeleteScheduleUseCase deleteScheduleUseCase;
  private final ScheduleFacadeService scheduleFacadeService;

  @PostMapping
  public ResponseEntity<String> createSchedule(
      @RequestBody ScheduleCreateByTextRequest request,
      @UserInfo UserDetails userDetails) throws JsonProcessingException {

    ScheduleCreateByTextCommand command = new ScheduleCreateByTextCommand(
        request.text(),
        userDetails.id()
    );

    Long scheduleId = scheduleFacadeService.createScheduleByText(command);

    return ResponseEntity.created(URI.create("/api/v1/schedules/" + scheduleId)).build();
  }

//  @GetMapping
//  public ResponseEntity<List<ScheduleResponse.WeekGroupedScheduleResponse>> getSchedules(
//      @RequestParam int year, @RequestParam int month,
//      @UserInfo UserDetails userDetails) {
//
//    GetScheduleListQuery query = new GetScheduleListQuery(userDetails.id(), year, month);
//
//    List<ScheduleResponse.WeekGroupedScheduleResponse> scheduleResponses = getScheduleUseCase.getScheduleList(
//        query);
//
//    return ResponseEntity.ok().body(scheduleResponses);
//  }

  @DeleteMapping("/{scheduleId}")
  public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId,
      @UserInfo UserDetails userDetails) {
    DeleteScheduleCommand command = DeleteScheduleCommand
        .builder()
        .scheduleId(scheduleId)
        .userId(userDetails.id())
        .build();

    deleteScheduleUseCase.deleteSchedule(command);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/counts")
  public ResponseEntity<V1_GetScheduleCount> getScheduleCounts(
      @RequestParam @DateTimeFormat(iso = ISO.DATE) @NotNull
      LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) @NotNull
      LocalDate endDate,
      @UserInfo UserDetails userDetails) {

    ScheduleCountsInfo info = scheduleFacadeService.getScheduleCounts(
        ScheduleCountCommand.from(userDetails.id(), startDate, endDate)
    );

    return ResponseEntity.ok().body(ScheduleResponse.V1_GetScheduleCount.from(info));
  }

  @GetMapping()
  public ResponseEntity<ScheduleGetListResponse> getSchedulesOnDate(
      @RequestParam @NotNull int year, @RequestParam @NotNull int month,
      @RequestParam @NotNull int day, @UserInfo UserDetails userDetails
  ) {
    ScheduleDetailsInfo info = scheduleFacadeService.getSchedulesOnDate(
        SchedulesGetOnDate.from(year, month, day, userDetails.id())
    );

    return ResponseEntity.ok().body(ScheduleGetListResponse.from(info));
  }

  @PostMapping("/{scheduleId}/actions/toggle-checked")
  public ResponseEntity<Void> checkSchedule(@PathVariable Long scheduleId) {
    scheduleFacadeService.checkSchedule(new ScheduleCheckCommand(scheduleId));

    return ResponseEntity.ok().build();
  }

}
