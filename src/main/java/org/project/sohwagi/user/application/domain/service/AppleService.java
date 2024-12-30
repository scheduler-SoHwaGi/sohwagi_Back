package org.project.sohwagi.user.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.adapter.in.web.response.AppleOAuthInfo;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.usecase.AppleLoginUseCase;
import org.project.sohwagi.user.application.port.out.ApplePort;
import org.springframework.stereotype.Service;

@UseCase
@Service
@RequiredArgsConstructor
public class AppleService implements AppleLoginUseCase {

  private final ApplePort applePort;

  @Override
  public LoginResult appleLogin(AppleLoginCommand command) {
    AppleOAuthInfo appleOAuthInfo = applePort.getAppleOAuthInfo(command.authorizationCode());

    return null;
  }
}
