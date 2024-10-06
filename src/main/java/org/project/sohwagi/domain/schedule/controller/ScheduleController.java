package org.project.sohwagi.domain.schedule.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;
import org.project.sohwagi.domain.schedule.dto.ScheduleResponse;
import org.project.sohwagi.domain.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;

	@PostMapping
	public ResponseEntity<String> createSchedule(@RequestBody ScheduleRequest request) {
		Long scheduleId = scheduleService.createSchedule(request);

		return ResponseEntity.created(URI.create("/api/v1/schedules/" + scheduleId)).build();
	}

	@GetMapping
	public ResponseEntity<List<ScheduleResponse>> getSchedules() {
		List<ScheduleResponse> scheduleResponses = scheduleService.getSchedules();

		return ResponseEntity.ok().body(scheduleResponses);
	}

	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
		scheduleService.deleteSchedule(scheduleId);

		return ResponseEntity.ok().build();
	}

}
