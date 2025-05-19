package org.project.sohwagi.presentation.res;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record LoginRes(
    @NotNull String accessToken,

    @NotNull String refreshToken
){
  @Builder
  public LoginRes(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
