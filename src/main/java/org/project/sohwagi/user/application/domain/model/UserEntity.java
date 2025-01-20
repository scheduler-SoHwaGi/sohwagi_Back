package org.project.sohwagi.user.application.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
public class UserEntity {

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

  @Column(name = "isDeleted", nullable = false)
  @Builder.Default
  private boolean isDeleted = false;

  @Column
  private String refreshToken;

  public void updateFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
  }

}
