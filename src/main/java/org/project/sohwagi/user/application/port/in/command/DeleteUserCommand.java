package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.UserEntity;

public record DeleteUserCommand(
    @NotNull
    UserEntity userEntity,

    @NotNull
    String refreshToken
) {

  @Builder
  public DeleteUserCommand(
      UserEntity userEntity,
      String refreshToken
  ){
    this.userEntity = userEntity;
    this.refreshToken = refreshToken;
    validate(this);
  }

}
