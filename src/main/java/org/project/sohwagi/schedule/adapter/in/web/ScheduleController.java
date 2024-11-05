package org.project.sohwagi.schedule.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleTextRequest;
import org.project.sohwagi.schedule.application.domain.service.ScheduleService;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;
import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;
import org.project.sohwagi.schedule.application.port.in.usecase.CreateScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.GetScheduleUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final CreateScheduleUseCase createScheduleUseCase;
	private final GetScheduleUseCase getScheduleUseCase;
	private final DeleteScheduleUseCase deleteScheduleUseCase;

	@PostMapping
	public ResponseEntity<String> createSchedule(@RequestBody ScheduleTextRequest request,
		@RequestAttribute("userId") Long userId)
		throws JsonProcessingException {

		CreateScheduleByTextCommand command = CreateScheduleByTextCommand
			.builder()
			.text(request.getText())
			.userId(userId)
			.build();

		Long scheduleId = createScheduleUseCase.createScheduleByText(command);

		return ResponseEntity.created(URI.create("/api/v1/schedules/" + scheduleId)).build();
	}

	@GetMapping
	public ResponseEntity<List<ScheduleResponse>> getSchedules(
		@RequestAttribute("userId") Long userId) {

		GetScheduleListQuery query = GetScheduleListQuery
			.builder()
			.userId(userId)
			.build();

		List<ScheduleResponse> scheduleResponses = getScheduleUseCase.getScheduleList(query);

		return ResponseEntity.ok().body(scheduleResponses);
	}

	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId,
		@RequestAttribute("userId") Long userId) {
		DeleteScheduleCommand command = DeleteScheduleCommand
			.builder()
			.scheduleId(scheduleId)
			.userId(userId)
			.build();

		deleteScheduleUseCase.deleteSchedule(command);

		return ResponseEntity.ok().build();
	}

}
