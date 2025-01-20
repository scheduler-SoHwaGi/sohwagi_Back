package org.project.sohwagi.user.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.adapter.in.web.response.AppleOAuthInfo;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.domain.model.TokenEntity;
import org.project.sohwagi.user.application.domain.model.UserEntity;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.command.UserCommand;
import org.project.sohwagi.user.application.port.in.usecase.AppleLoginUseCase;
import org.project.sohwagi.user.application.port.out.ApplePort;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.SaveRefreshTokenPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public LoginResult appleLogin(AppleLoginCommand command) {
    AppleOAuthInfo appleOAuthInfo = applePort.getAppleOAuthInfo(command.authorizationCode());

    UserEntity userEntity = getOrCreateUser(command.userName(), appleOAuthInfo.email(), "apple",
        appleOAuthInfo.subject(), appleOAuthInfo.refreshToken());

    String accessToken = jwtUtil.createAccessToken(userEntity.getId());
    String refreshToken = jwtUtil.createRefreshToken(userEntity.getId());

    TokenEntity tokenEntity = TokenEntity
        .builder()
        .refreshToken(refreshToken)
        .isExpired(false)
        .userEntity(userEntity)
        .build();

    saveRefreshTokenPort.saveRefreshToken(tokenEntity);

    return LoginResult
        .builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public void appleRevoke(UserCommand userCommand) {
    applePort.revoke(userCommand.userEntity());
  }

   private UserEntity getOrCreateUser(String userName, String email, String oauthProvider,
      String subject, String appleRefreshToken) {
    return loadUserPort.loadUserByOAuthProviderAndOAuthSubject(oauthProvider, subject)
        .orElseGet(() -> {
          UserEntity newUserEntity = UserEntity
              .builder()
              .userName(userName)
              .email(email)
              .oauthSubject(subject)
              .oauthProvider(oauthProvider)
              .refreshToken(appleRefreshToken)
              .build();
          return saveUserPort.saveUser(newUserEntity);
        });
  }

}
