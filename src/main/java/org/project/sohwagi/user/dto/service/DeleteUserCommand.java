package org.project.sohwagi.user.dto.service;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.User;

public record DeleteUserCommand(
    @NotNull
    User user,

    @NotNull
    String refreshToken
) {

  @Builder
  public DeleteUserCommand(
      User user,
      String refreshToken
  ){
    this.user = user;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
