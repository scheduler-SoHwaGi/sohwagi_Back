package org.project.sohwagi.schedule.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleTextRequest;
import org.project.sohwagi.schedule.application.domain.service.ScheduleService;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;
import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;
import org.project.sohwagi.schedule.application.port.in.usecase.CreateScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.GetScheduleUseCase;
import org.project.sohwagi.user.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final CreateScheduleUseCase createScheduleUseCase;
	private final GetScheduleUseCase getScheduleUseCase;
	private final DeleteScheduleUseCase deleteScheduleUseCase;

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

	@GetMapping
	public ResponseEntity<List<ScheduleResponse.WeekGroupedScheduleResponse>> getSchedules(
			@RequestParam int year, @RequestParam int month,
			@UserInfo UserDetails userDetails) {

		GetScheduleListQuery query = new GetScheduleListQuery(userDetails.id(), year, month);

		List<ScheduleResponse.WeekGroupedScheduleResponse> scheduleResponses = getScheduleUseCase.getScheduleList(query);

		return ResponseEntity.ok().body(scheduleResponses);
	}

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

}
