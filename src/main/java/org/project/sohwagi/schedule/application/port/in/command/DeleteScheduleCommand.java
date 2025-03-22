package org.project.sohwagi.schedule.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record DeleteScheduleCommand(
	@NotNull(message = "scheduleId is required")
	Long scheduleId,
	@NotNull(message = "userId is required")
	Long userId
) {
	@Builder
	public DeleteScheduleCommand(
		Long scheduleId,
		Long userId
	) {
		this.scheduleId = scheduleId;
		this.userId = userId;
		validate(this);
	}

}
