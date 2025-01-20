package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.UserEntity;

public record UserCommand(
    @NotNull
    UserEntity userEntity
) {
  @Builder
  public UserCommand(
      UserEntity userEntity
  ){
    this.userEntity = userEntity;
    validate(this);
  }

}
