package org.project.sohwagi.user.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.UserEntity;

public record SaveFcmTokenCommand(
	@NotNull(message = "fcmToken is required")
	String fcmToken,
	@NotNull(message = "user is required")
  UserEntity userEntity
) {
	@Builder
	public SaveFcmTokenCommand(
		String fcmToken,
		UserEntity userEntity
	) {
		this.fcmToken = fcmToken;
		this.userEntity = userEntity;
	}
}
