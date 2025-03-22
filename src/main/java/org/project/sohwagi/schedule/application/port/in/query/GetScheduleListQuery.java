package org.project.sohwagi.schedule.application.port.in.query;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record GetScheduleListQuery(
	@NotNull(message = "userId is required")
	Long userId
) {
	@Builder
	public GetScheduleListQuery(
		Long userId
	) {
		this.userId = userId;
		validate(this);
	}

}
