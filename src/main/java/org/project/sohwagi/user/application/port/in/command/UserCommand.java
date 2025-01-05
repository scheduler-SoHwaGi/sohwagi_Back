package org.project.sohwagi.user.application.port.in.command;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.User;

public record UserCommand(
    @NotNull
    User user
) {
  @Builder
  public UserCommand(
      User user
  ){
    this.user = user;
    validate(this);
  }

}
