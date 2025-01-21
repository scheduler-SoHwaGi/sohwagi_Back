package org.project.sohwagi.user.dto.service;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.UserDetails;

public record DeleteUserCommand(
    @NotNull
    UserDetails userDetails,

    @NotNull
    String refreshToken
) {

  @Builder
  public DeleteUserCommand(
      UserDetails userDetails,
      String refreshToken
  ){
    this.userDetails = userDetails;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
