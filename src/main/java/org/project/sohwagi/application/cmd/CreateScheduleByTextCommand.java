package org.project.sohwagi.application.cmd;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CreateScheduleByTextCommand(
	@NotNull(message = "text is required")
	String text,
	@NotNull(message = "text is required")
	Long userId
) {
	@Builder
	public CreateScheduleByTextCommand(
		String text,
		Long userId
	) {
		this.text = text;
		this.userId = userId;
		validate(this);
	}

}
