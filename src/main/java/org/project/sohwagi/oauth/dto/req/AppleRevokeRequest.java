package org.project.sohwagi.oauth.dto.req;

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
