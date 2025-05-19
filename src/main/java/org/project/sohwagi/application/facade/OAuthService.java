package org.project.sohwagi.application.facade;

import java.util.ArrayList;
import java.util.List;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.service.TokenService;
import org.project.sohwagi.infra.apple.AppleClient;
import org.project.sohwagi.infra.apple.AppleOAuthInfoRes;
import org.project.sohwagi.presentation.res.LoginRes;
import org.project.sohwagi.application.cmd.AppleLoginCommand;
import org.project.sohwagi.application.cmd.RefreshTokenCommand;
import org.project.sohwagi.application.service.UserService;
import org.project.sohwagi.application.cmd.DeleteUserCommand;
import org.project.sohwagi.application.cmd.GetOrCreateUserCommand;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthService {

  private final AppleClient appleClient;
  private final UserService userService;
  private final TokenService tokenService;
  private final JwtUtil jwtUtil;
  private final ScheduleService scheduleService;

  public OAuthService(
      AppleClient appleClient,
      JwtUtil jwtUtil,
      UserService userService,
      TokenService tokenService,
      ScheduleService scheduleService
  ) {
    this.appleClient = appleClient;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
    this.tokenService = tokenService;
    this.scheduleService = scheduleService;
  }

  @Transactional
  public LoginRes appleLogin(AppleLoginCommand command) {
    AppleOAuthInfoRes appleOAuthInfoRes = appleClient.getAppleOAuthInfo(command);

    GetOrCreateUserCommand getOrCreateUserCommand = new GetOrCreateUserCommand(command.userName(),
        appleOAuthInfoRes.email(), "apple", appleOAuthInfoRes.subject(),
        appleOAuthInfoRes.refreshToken());

    Long userId = userService.getOrCreateUser(getOrCreateUserCommand);

    String accessToken = jwtUtil.createAccessToken(userId);
    String refreshToken = jwtUtil.createRefreshToken(userId);

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(refreshToken);
    String token = tokenService.saveToken(refreshTokenCommand);

    return LoginRes.builder()
        .accessToken(accessToken)
        .refreshToken(token)
        .build();
  }

  @Transactional
  public void deleteAppleUser(DeleteUserCommand deleteUserCommand) {
    appleClient.appleRevoke(deleteUserCommand);

    RefreshTokenCommand refreshTokenCommand = RefreshTokenCommand.builder().refreshToken(
        deleteUserCommand.refreshToken()).build();

    tokenService.expireToken(refreshTokenCommand);

    scheduleService.deleteScheduleByUserRevoke(deleteUserCommand.userDetails().id());

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

  @Transactional
  public LoginRes qaLogin() {
    GetOrCreateUserCommand getOrCreateUserCommand = new GetOrCreateUserCommand("test", null, "test",
        null, null);

    Long userId = userService.getOrCreateUser(getOrCreateUserCommand);

    String accessToken = jwtUtil.createAccessToken(userId);
    String refreshToken = jwtUtil.createRefreshToken(userId);

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(refreshToken);
    String token = tokenService.saveToken(refreshTokenCommand);

    return LoginRes.builder()
        .accessToken(accessToken)
        .refreshToken(token)
        .build();
  }
}
