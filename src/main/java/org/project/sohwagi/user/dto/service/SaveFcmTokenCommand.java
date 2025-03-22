package org.project.sohwagi.user.dto.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.project.sohwagi.user.UserDetails;

public record SaveFcmTokenCommand(
    @NotNull(message = "fcmToken is required")
    String fcmToken,
    @NotNull(message = "user is required")
    UserDetails userDetails
) {

  @Builder
  public SaveFcmTokenCommand(
      String fcmToken,
      UserDetails userDetails
  ) {
    this.fcmToken = fcmToken;
    this.userDetails = userDetails;
  }
}
