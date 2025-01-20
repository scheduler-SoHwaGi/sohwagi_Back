package org.project.sohwagi.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.user.adapter.in.web.request.UserFcmTokenRequest;
import org.project.sohwagi.user.adapter.in.web.request.UserNickNameRequest;
import org.project.sohwagi.user.adapter.in.web.response.GetUserInfo;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.domain.model.UserEntity;
import org.project.sohwagi.user.application.port.in.command.CreateUserByUserNameCommand;
import org.project.sohwagi.user.application.port.in.command.LogoutCommand;
import org.project.sohwagi.user.application.port.in.command.SaveFcmTokenCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.project.sohwagi.user.application.port.in.usecase.LogoutUseCase;
import org.project.sohwagi.user.application.port.in.usecase.SaveFcmTokenUseCase;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;
  private final SaveFcmTokenUseCase saveFcmTokenUseCase;
  private final LogoutUseCase logoutUseCase;

  @PostMapping("/log-in/nicknames")
  public ResponseEntity<String> login(@RequestBody UserNickNameRequest request) {
    CreateUserByUserNameCommand command = CreateUserByUserNameCommand
        .builder()
        .userName(request.getNickName())
        .build();

    createUserUseCase.createUserByNickName(command);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/fcmTokens")
  public ResponseEntity<String> login(@RequestBody UserFcmTokenRequest request,
      @UserInfo UserEntity userEntity) {
    SaveFcmTokenCommand command = SaveFcmTokenCommand
        .builder()
        .fcmToken(request.getFcmToken())
        .userEntity(userEntity)
        .build();

    saveFcmTokenUseCase.saveFcmToken(command);

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("X-REFRESH-TOKEN") String refreshToken) {
    LogoutCommand logoutCommand = LogoutCommand
        .builder().refreshToken(refreshToken).build();

    logoutUseCase.logout(logoutCommand);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/me")
  public ResponseEntity<GetUserInfo> getUserInfo(@UserInfo
  User user) {
    GetUserInfo getUserInfo = new GetUserInfo(user.name(), user.email());

    return ResponseEntity.ok(getUserInfo);
  }
}
