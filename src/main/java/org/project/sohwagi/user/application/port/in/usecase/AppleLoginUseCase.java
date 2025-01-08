package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.adapter.in.web.request.AppleRevokeRequest;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.command.DeleteUserCommand;
import org.project.sohwagi.user.application.port.in.command.UserCommand;

public interface AppleLoginUseCase {

  LoginResult appleLogin(AppleLoginCommand command);

  void appleRevoke(UserCommand command);
}
