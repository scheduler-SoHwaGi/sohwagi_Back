package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record LogoutCommand(
    @NotNull
    String refreshToken
) {
  @Builder
  public LogoutCommand(String refreshToken) {
    this.refreshToken = refreshToken;
    validate(this);
  }
}
