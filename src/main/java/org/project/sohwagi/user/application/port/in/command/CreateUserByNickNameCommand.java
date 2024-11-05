package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CreateUserByNickNameCommand(
	@NotNull(message = "nickName is required")
	String nickName
) {
	@Builder
	public CreateUserByNickNameCommand(
		String nickName
	) {
		this.nickName = nickName;
		validate(this);
	}

}
