package org.project.sohwagi.user;

public record UserDetails(
    Long id,
    String fcmToken,
    String userName,
    String oauthProvider,
    String oauthSubject,
    String email,
    boolean isDeleted,
    String appleRefreshToken) {

  public UserDetails(
      Long id,
      String fcmToken,
      String userName,
      String oauthProvider,
      String oauthSubject,
      String email,
      boolean isDeleted,
      String appleRefreshToken
  ) {
    this.id = id;
    this.fcmToken = fcmToken;
    this.userName = userName;
    this.oauthProvider = oauthProvider;
    this.oauthSubject = oauthSubject;
    this.email = email;
    this.isDeleted = isDeleted;
    this.appleRefreshToken = appleRefreshToken;
  }

  public User toEntity() {
    return new User(
        id,
        fcmToken,
        userName,
        oauthProvider,
        oauthSubject,
        email,
        isDeleted,
        appleRefreshToken
    );
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
