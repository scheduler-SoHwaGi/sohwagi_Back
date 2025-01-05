package org.project.sohwagi.user.adapter.in.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.user.adapter.in.web.request.AppleLoginRequest;
import org.project.sohwagi.user.adapter.in.web.response.LoginResult;
import org.project.sohwagi.user.application.port.in.command.AppleLoginCommand;
import org.project.sohwagi.user.application.port.in.usecase.AppleLoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth")
public class OAuthController {

  private final AppleLoginUseCase appleLoginUseCase;

  @PostMapping("/apple")
  public ResponseEntity<?> appleLogin(@RequestBody AppleLoginRequest request) {

    AppleLoginCommand command = AppleLoginCommand
        .builder()
        .authorizationCode(request.authorizationCode())
        .userName(request.userName())
        .build();

    LoginResult loginResult = appleLoginUseCase.appleLogin(command);

    return ResponseEntity.ok(loginResult);
  }

}
