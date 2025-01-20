package org.project.sohwagi.user;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

  private Long id;

  private String fcmToken;

  private String userName;

  private String oauthProvider;

  private String oauthSubject;

  private String email;

  private boolean isDeleted;

  private String appleRefreshToken;

  public UserEntity toEntity() {
    return new UserEntity(
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

  public static User from(UserEntity userEntity) {
    return new User(
        userEntity.getId(),
        userEntity.getFcmToken(),
        userEntity.getUserName(),
        userEntity.getOauthProvider(),
        userEntity.getOauthSubject(),
        userEntity.getEmail(),
        userEntity.isDeleted(),
        userEntity.getAppleRefreshToken()
    );
  }


  public void updateFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }
    return isDeleted == user.isDeleted && Objects.equals(id, user.id)
        && Objects.equals(fcmToken, user.fcmToken) && Objects.equals(userName,
        user.userName) && Objects.equals(oauthProvider, user.oauthProvider)
        && Objects.equals(oauthSubject, user.oauthSubject) && Objects.equals(
        email, user.email) && Objects.equals(appleRefreshToken, user.appleRefreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fcmToken, userName, oauthProvider, oauthSubject, email, isDeleted,
        appleRefreshToken);
  }
}
