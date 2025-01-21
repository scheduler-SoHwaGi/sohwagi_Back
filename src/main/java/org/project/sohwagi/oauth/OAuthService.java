package org.project.sohwagi.oauth;

import java.util.ArrayList;
import java.util.List;
import org.project.sohwagi.oauth.dto.res.AppleOAuthInfoRes;
import org.project.sohwagi.oauth.dto.res.AppleLoginRes;
import org.project.sohwagi.oauth.dto.service.AppleLoginCommand;
import org.project.sohwagi.token.dto.service.RefreshTokenCommand;
import org.project.sohwagi.token.TokenService;
import org.project.sohwagi.user.UserService;
import org.project.sohwagi.user.dto.service.DeleteUserCommand;
import org.project.sohwagi.user.dto.service.GetOrCreateUserCommand;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

  private final AppleService appleService;
  private final UserService userService;
  private final TokenService tokenService;
  private final JwtUtil jwtUtil;

  public OAuthService(
      AppleService appleService,
      JwtUtil jwtUtil,
      UserService userService,
      TokenService tokenService
  ) {
    this.appleService = appleService;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  public AppleLoginRes appleLogin(AppleLoginCommand command) {
    AppleOAuthInfoRes appleOAuthInfoRes = appleService.getAppleOAuthInfo(command);

    GetOrCreateUserCommand getOrCreateUserCommand = new GetOrCreateUserCommand(command.userName(),
        appleOAuthInfoRes.email(), "apple", appleOAuthInfoRes.subject(),
        appleOAuthInfoRes.refreshToken());

    Long userId = userService.getOrCreateUser(getOrCreateUserCommand);

    String accessToken = jwtUtil.createAccessToken(userId);
    String refreshToken = jwtUtil.createRefreshToken(userId);

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(refreshToken);
    String token = tokenService.saveToken(refreshTokenCommand);

    return AppleLoginRes.builder()
        .accessToken(accessToken)
        .refreshToken(token)
        .build();
  }

  public void deleteAppleUser(DeleteUserCommand deleteUserCommand) {
    appleService.appleRevoke(deleteUserCommand);

    RefreshTokenCommand refreshTokenCommand = RefreshTokenCommand.builder().refreshToken(
        deleteUserCommand.refreshToken()).build();

    tokenService.expireToken(refreshTokenCommand);

    userService.deleteUser(
        deleteUserCommand.userDetails()
    );
  }

  public List<String> testLogin(String name) {
    List<String> res = new ArrayList<>();

    GetOrCreateUserCommand getOrCreateUserCommand = new GetOrCreateUserCommand(name,
        null, "test", "1234", null);

    Long userId = userService.getOrCreateUser(getOrCreateUserCommand);

    String accessToken = jwtUtil.createAccessToken(userId);
    String refreshToken = jwtUtil.createRefreshToken(userId);

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(refreshToken);
    String token = tokenService.saveToken(refreshTokenCommand);

    res.add(accessToken);
    res.add(token);

    return res;
  }
}
