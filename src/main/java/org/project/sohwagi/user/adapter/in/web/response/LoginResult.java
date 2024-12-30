package org.project.sohwagi.user.adapter.in.web.response;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.application.domain.model.User;

public record LoginResult (
    @NotNull User user,
    @NotNull String jwt
){
  @Builder
  public LoginResult(User user, String jwt) {
    this.user = user;
    this.jwt = jwt;
    validate(this);
  }

}
