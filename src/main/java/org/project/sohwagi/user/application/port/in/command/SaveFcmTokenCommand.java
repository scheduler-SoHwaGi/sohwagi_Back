package org.project.sohwagi.user.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record SaveFcmTokenCommand(
	@NotNull(message = "nickName is required")
	String fcmToken,
	@NotNull(message = "nickName is required")
	Long userId
) {
	@Builder
	public SaveFcmTokenCommand(
		String fcmToken,
		Long userId
	) {
		this.fcmToken = fcmToken;
		this.userId = userId;
	}
}
