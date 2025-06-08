package org.project.sohwagi.domain;

public record UserDetails(
    Long id,
    String fcmToken,
    String userName,
    String oauthProvider,
    String oauthSubject,
    String email,
    boolean isDeleted,
    String appleRefreshToken) {

  public User toEntity() {
    return User.builder()
        .id(id)
        .fcmToken(fcmToken)
        .userName(userName)
        .oauthProvider(oauthProvider)
        .oauthSubject(oauthSubject)
        .email(email)
        .isDeleted(isDeleted)
        .appleRefreshToken(appleRefreshToken)
        .build();
  }

  public static UserDetails from(User user) {
    return new UserDetails(
        user.getId(),
        user.getFcmToken(),
        user.getUserName(),
        user.getOauthProvider(),
        user.getOauthSubject(),
        user.getEmail(),
        user.isDeleted(),
        user.getAppleRefreshToken()
    );
  }
}
