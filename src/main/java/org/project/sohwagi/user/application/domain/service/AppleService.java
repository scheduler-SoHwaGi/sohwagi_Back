package org.project.sohwagi.user.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.adapter.in.web.response.AppleOAuthInfo;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.domain.model.Token;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.usecase.AppleLoginUseCase;
import org.project.sohwagi.user.application.port.out.ApplePort;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.SaveRefreshTokenPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.stereotype.Service;

@UseCase
@Service
@RequiredArgsConstructor
public class AppleService implements AppleLoginUseCase {

  private final ApplePort applePort;
  private final LoadUserPort loadUserPort;
  private final SaveUserPort saveUserPort;
  private final SaveRefreshTokenPort saveRefreshTokenPort;
  private final JwtUtil jwtUtil;

  @Override
  public LoginResult appleLogin(AppleLoginCommand command) {
    AppleOAuthInfo appleOAuthInfo = applePort.getAppleOAuthInfo(command.authorizationCode());

    User user = getOrCreateUser(command.userName(), appleOAuthInfo.email(), "apple",
        appleOAuthInfo.subject());

    String accessToken = jwtUtil.createAccessToken(user.getId());
    String refreshToken = jwtUtil.createRefreshToken(user.getId());

    Token token = Token
        .builder()
        .refreshToken(refreshToken)
        .isExpired(false)
        .user(user)
        .build();

    saveRefreshTokenPort.saveRefreshToken(token);

    return LoginResult
        .builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private User getOrCreateUser(String userName, String email, String oauthProvider,
      String subject) {
    return loadUserPort.loadUserByOAuthProviderAndOAuthSubject(oauthProvider, subject)
        .orElseGet(() -> {
          User newUser = User
              .builder()
              .userName(userName)
              .email(email)
              .oauthSubject(subject)
              .oauthProvider(oauthProvider)
              .build();
          return saveUserPort.saveUser(newUser);
        });
  }

}
