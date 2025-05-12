package org.project.sohwagi.schedule.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleTextRequest;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse.V1_GetList;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse.V1_GetScheduleCount;
import org.project.sohwagi.schedule.application.ScheduleFacadeService;
import org.project.sohwagi.schedule.application.cmd.ScheduleCommand;
import org.project.sohwagi.schedule.application.info.ScheduleInfo;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;
import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;
import org.project.sohwagi.schedule.application.port.in.usecase.CreateScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.GetScheduleUseCase;
import org.project.sohwagi.user.UserDetails;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final CreateScheduleUseCase createScheduleUseCase;
  private final DeleteScheduleUseCase deleteScheduleUseCase;
  private final ScheduleFacadeService scheduleFacadeService;

  @PostMapping
  public ResponseEntity<String> createSchedule(@RequestBody ScheduleTextRequest request,
      @UserInfo UserDetails userDetails)
      throws JsonProcessingException {

    CreateScheduleByTextCommand command = CreateScheduleByTextCommand
        .builder()
        .text(request.getText())
        .userId(userDetails.id())
        .build();

    Long scheduleId = createScheduleUseCase.createScheduleByText(command);

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
  public ResponseEntity<V1_GetScheduleCount> V1_Get_Schedule_Counts(
      @RequestParam @DateTimeFormat(iso = ISO.DATE) @NotNull
      LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) @NotNull
      LocalDate endDate,
      @UserInfo UserDetails userDetails) {

    ScheduleInfo.ScheduleCounts info = scheduleFacadeService.getScheduleCounts(
        ScheduleCommand.CountScheduleUseCase.from(userDetails.id(), startDate, endDate)
    );

    return ResponseEntity.ok().body(ScheduleResponse.V1_GetScheduleCount.from(info));
  }

  @GetMapping()
  public ResponseEntity<V1_GetList> V1_Get_Schedules_On_Date(
      @RequestParam @NotNull int year, @RequestParam @NotNull int month,
      @RequestParam @NotNull int day, @UserInfo UserDetails userDetails
  ) {
    ScheduleInfo.ScheduleDetails info = scheduleFacadeService.getSchedulesOnDate(
        ScheduleCommand.GetSchedulesOnDateUseCase.from(year, month, day, userDetails.id())
    );

    return ResponseEntity.ok().body(ScheduleResponse.V1_GetList.from(info));
  }

}
