package org.project.sohwagi.token.dto.service;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record RefreshTokenCommand(
    @NotNull
    String refreshToken
) {
  @Builder
  public RefreshTokenCommand(
      String refreshToken
  ){
    this.refreshToken = refreshToken;
    validate(this);
  }
}
