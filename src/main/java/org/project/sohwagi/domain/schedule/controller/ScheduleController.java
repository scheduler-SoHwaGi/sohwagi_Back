package org.project.sohwagi.domain.schedule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.domain.schedule.dto.ScheduleResponse;
import org.project.sohwagi.domain.schedule.dto.ScheduleTextRequest;
import org.project.sohwagi.domain.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;

	@PostMapping
	public ResponseEntity<String> createSchedule(@RequestBody ScheduleTextRequest request,
		@RequestAttribute("userId") Long userId)
		throws JsonProcessingException {
		Long scheduleId = scheduleService.createSchedule(request, userId);

		return ResponseEntity.created(URI.create("/api/v1/schedules/" + scheduleId)).build();
	}

	@GetMapping
	public ResponseEntity<List<ScheduleResponse>> getSchedules(
		@RequestAttribute("userId") Long userId) {
		List<ScheduleResponse> scheduleResponses = scheduleService.getSchedules(userId);

		return ResponseEntity.ok().body(scheduleResponses);
	}

	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId,
		@RequestAttribute("userId") Long userId) {
		scheduleService.deleteSchedule(scheduleId, userId);

		return ResponseEntity.ok().build();
	}

}
