package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.application.port.in.command.LogoutCommand;

public interface LogoutUseCase {

  void logout(LogoutCommand command);

}
