package org.project.sohwagi.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.user.adapter.in.web.request.AppleLoginRequest;
import org.project.sohwagi.user.adapter.in.web.request.AppleRevokeRequest;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.command.DeleteUserCommand;
import org.project.sohwagi.user.application.port.in.command.UserCommand;
import org.project.sohwagi.user.application.port.in.usecase.AppleLoginUseCase;
import org.project.sohwagi.user.application.port.in.usecase.DeleteUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

  private final AppleLoginUseCase appleLoginUseCase;
  private final DeleteUserUseCase deleteUserUseCase;

  @PostMapping("/apple/login")
  public ResponseEntity<?> appleLogin(@RequestBody AppleLoginRequest request) {

    AppleLoginCommand command = AppleLoginCommand
        .builder()
        .authorizationCode(request.authorizationCode())
        .userName(request.userName())
        .build();

    LoginResult loginResult = appleLoginUseCase.appleLogin(command);

    return ResponseEntity.ok(loginResult);
  }

  @DeleteMapping("/apple/revoke")
  public ResponseEntity<String> deleteUser(@RequestHeader("X-REFRESH-TOKEN") String refreshToken,
      @UserInfo User user) {
    DeleteUserCommand deleteUserCommand = DeleteUserCommand
        .builder()
        .user(user)
        .refreshToken(refreshToken)
        .build();

    UserCommand userCommand = UserCommand
        .builder().user(user).build();

    appleLoginUseCase.appleRevoke(userCommand);
    deleteUserUseCase.deleteUser(deleteUserCommand);

    return ResponseEntity.ok().build();
  }

}
