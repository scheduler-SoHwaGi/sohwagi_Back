package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.application.port.in.command.CreateUserByNickNameCommand;

public interface CreateUserUseCase {

	void createUserByNickName(CreateUserByNickNameCommand command);

}
