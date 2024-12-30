package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;

public interface AppleLoginUseCase {

  LoginResult appleLogin(AppleLoginCommand command);
}
