package org.project.sohwagi.oauth.dto.res;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record AppleLoginRes(
    @NotNull String accessToken,

    @NotNull String refreshToken
){
  @Builder
  public AppleLoginRes(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
