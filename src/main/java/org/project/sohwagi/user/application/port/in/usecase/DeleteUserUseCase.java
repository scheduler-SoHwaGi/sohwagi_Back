package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.application.port.in.command.DeleteUserCommand;

public interface DeleteUserUseCase {

  void deleteUser(DeleteUserCommand command);

}
