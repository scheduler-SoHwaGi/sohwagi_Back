package org.project.sohwagi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String fcmToken;

  @Column
  private String userName;

  @Column
  private String oauthProvider;

  @Column
  private String oauthSubject;

  @Column
  private String email;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

  @Column
  private String appleRefreshToken;

  @Column
  private LocalDateTime createdAt;

  @Builder
  private User(
      Long id,
      String fcmToken,
      String userName,
      String oauthProvider,
      String oauthSubject,
      String email,
      boolean isDeleted,
      String appleRefreshToken) {
    this.fcmToken = fcmToken;
    this.id = id;
    this.userName = userName;
    this.oauthProvider = oauthProvider;
    this.oauthSubject = oauthSubject;
    this.email = email;
    this.isDeleted = isDeleted;
    this.appleRefreshToken = appleRefreshToken;
    this.createdAt = LocalDateTime.now();
  }

  public static User create(String userName, String oauthProvider, String email) {
    return User.builder().userName(userName).oauthProvider(oauthProvider).email(email).build();
  }

  public void updateFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
  }

  public void reLogin() {
    this.isDeleted = false;
  }

}
