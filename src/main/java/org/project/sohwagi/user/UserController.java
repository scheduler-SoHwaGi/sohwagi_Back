package org.project.sohwagi.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.token.TokenService;
import org.project.sohwagi.token.dto.service.RefreshTokenCommand;
import org.project.sohwagi.user.dto.service.SaveFcmTokenCommand;
import org.project.sohwagi.user.dto.req.PostFcmTokenReq;
import org.project.sohwagi.user.dto.res.GetUserInfoRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final TokenService tokenService;

  @PostMapping("/fcmTokens")
  public ResponseEntity<String> saveFcmToken(@RequestBody PostFcmTokenReq request,
      @UserInfo UserDetails userDetails) {
    SaveFcmTokenCommand command = SaveFcmTokenCommand
        .builder()
        .fcmToken(request.getFcmToken())
        .userDetails(userDetails)
        .build();

    userService.saveFcmToken(command);

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("X-REFRESH-TOKEN") String refreshToken) {

    RefreshTokenCommand refreshTokenCommand = RefreshTokenCommand
        .builder().refreshToken(refreshToken).build();

    tokenService.expireToken(refreshTokenCommand);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/me")
  public ResponseEntity<GetUserInfoRes> getUserInfo(@UserInfo
  UserDetails userDetails) {
    GetUserInfoRes getUserInfoRes = userService.getUserInfo(userDetails);

    return ResponseEntity.ok(getUserInfoRes);
  }
}
