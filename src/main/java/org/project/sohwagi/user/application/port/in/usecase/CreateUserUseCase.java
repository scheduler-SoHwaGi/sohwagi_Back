package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.application.port.in.command.CreateUserByUserNameCommand;

public interface CreateUserUseCase {

	void createUserByNickName(CreateUserByUserNameCommand command);

}
