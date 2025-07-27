package org.project.sohwagi.application.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.project.sohwagi.application.service.AppleCredentialService;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.service.TokenService;
import org.project.sohwagi.domain.AppleCredential;
import org.project.sohwagi.domain.User;
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
  private final AppleCredentialService appleCredentialService;

  public OAuthService(
      AppleClient appleClient,
      JwtUtil jwtUtil,
      UserService userService,
      TokenService tokenService,
      ScheduleService scheduleService,
      AppleCredentialService appleCredentialService) {
    this.appleClient = appleClient;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
    this.tokenService = tokenService;
    this.scheduleService = scheduleService;
    this.appleCredentialService = appleCredentialService;
  }

  @Transactional
  public LoginRes appleLogin(AppleLoginCommand command) {
    AppleOAuthInfoRes appleOAuthInfoRes = fetchAppleOAuthInfo(command);
    Long userId = resolveUserId(appleOAuthInfoRes, command.userName());
    String accessToken = generateAccessToken(userId);
    String refreshToken = generateAndSaveRefreshToken(userId);
    return buildLoginResponse(accessToken, refreshToken);
  }

  @Transactional
  public void deleteAppleUser(DeleteUserCommand deleteUserCommand) {
    AppleCredential appleCredential = appleCredentialService.getAppleCredentialByUserId(
        deleteUserCommand.userDetails().id());
    appleClient.appleRevoke(appleCredential.getAppleRefreshToken());
    appleCredentialService.deleteAppleCredential(appleCredential);

    RefreshTokenCommand refreshTokenCommand = RefreshTokenCommand.builder().refreshToken(
        deleteUserCommand.refreshToken()).build();

    tokenService.expireToken(refreshTokenCommand);

    scheduleService.deleteScheduleByUserRevoke(deleteUserCommand.userDetails().id());

    userService.deleteUser(deleteUserCommand.userDetails());
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

  private AppleOAuthInfoRes fetchAppleOAuthInfo(AppleLoginCommand appleLoginCommand) {
    return appleClient.getAppleOAuthInfo(appleLoginCommand);
  }

  private Long resolveUserId(AppleOAuthInfoRes appleOAuthInfoRes, String userName) {
    return findExistingUserId(appleOAuthInfoRes.subject())
        .orElseGet(() -> createNewUser(appleOAuthInfoRes, userName));
  }

  private LoginRes buildLoginResponse(String accessToken, String refreshToken) {
    return LoginRes.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private Optional<Long> findExistingUserId(String subject) {
    return appleCredentialService.findAppleCredential(subject).map(AppleCredential::getUserId);
  }

  private Long createNewUser(AppleOAuthInfoRes oAuthInfo, String userName) {
    User newUser = userService.saveUser(userName, "APPLE", oAuthInfo.email());
    appleCredentialService.saveAppleCredential(
        oAuthInfo.subject(), oAuthInfo.refreshToken(), newUser.getId());
    return newUser.getId();
  }

  private String generateAccessToken(Long userId) {
    return jwtUtil.createAccessToken(userId);
  }

  private String generateAndSaveRefreshToken(Long userId) {
    String refreshToken = jwtUtil.createRefreshToken(userId);

    RefreshTokenCommand command = new RefreshTokenCommand(refreshToken);
    return tokenService.saveToken(command);
  }

}
