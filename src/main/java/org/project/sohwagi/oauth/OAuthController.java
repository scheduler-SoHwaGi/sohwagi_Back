package org.project.sohwagi.oauth;

import org.project.sohwagi.common.UserInfo;
import org.project.sohwagi.oauth.dto.req.AppleLoginRequest;
import org.project.sohwagi.oauth.dto.res.PostAppleLoginRes;
import org.project.sohwagi.user.User;
import org.project.sohwagi.oauth.dto.service.AppleLoginCommand;
import org.project.sohwagi.user.dto.service.DeleteUserCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

  private final OAuthService oAuthService;

  public OAuthController(OAuthService oAuthService) {
    this.oAuthService = oAuthService;
  }

  @PostMapping("/apple/login")
  public ResponseEntity<?> appleLogin(@RequestBody AppleLoginRequest request) {

    AppleLoginCommand command = AppleLoginCommand
        .builder()
        .authorizationCode(request.authorizationCode())
        .userName(request.userName())
        .build();

    PostAppleLoginRes postAppleLoginRes = oAuthService.appleLogin(command);

    return ResponseEntity.ok(postAppleLoginRes);
  }

  @DeleteMapping("/apple/revoke")
  public ResponseEntity<String> deleteAppleUser(@RequestHeader("X-REFRESH-TOKEN") String refreshToken,
      @UserInfo User user) {

    DeleteUserCommand deleteUserCommand = DeleteUserCommand
        .builder()
        .user(user)
        .refreshToken(refreshToken)
        .build();

    oAuthService.deleteAppleUser(deleteUserCommand);

    return ResponseEntity.ok().build();
  }

}
