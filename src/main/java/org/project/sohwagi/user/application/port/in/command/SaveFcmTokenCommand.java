package org.project.sohwagi.user.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.User;

public record SaveFcmTokenCommand(
	@NotNull(message = "fcmToken is required")
	String fcmToken,
	@NotNull(message = "user is required")
	User user
) {
	@Builder
	public SaveFcmTokenCommand(
		String fcmToken,
		User user
	) {
		this.fcmToken = fcmToken;
		this.user = user;
	}
}
