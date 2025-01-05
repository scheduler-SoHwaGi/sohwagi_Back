package org.project.sohwagi.user.adapter.in.web.request;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record AppleRevokeRequest(
    @NotNull
    String authorizationCode
) {

  @Builder
  public AppleRevokeRequest(
      String authorizationCode
  ) {
    this.authorizationCode = authorizationCode;
    validate(this);
  }

}
