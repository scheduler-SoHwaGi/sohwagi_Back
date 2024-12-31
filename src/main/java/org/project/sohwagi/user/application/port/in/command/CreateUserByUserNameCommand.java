package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CreateUserByUserNameCommand(
	@NotNull(message = "userName is required")
	String userName
) {
	@Builder
	public CreateUserByUserNameCommand(
		String userName
	) {
		this.userName = userName;
		validate(this);
	}

}
