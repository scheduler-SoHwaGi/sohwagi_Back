package org.project.sohwagi.user.adapter.in.web.response;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record LoginResult (
    @NotNull String accessToken,

    @NotNull String refreshToken
){
  @Builder
  public LoginResult(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
