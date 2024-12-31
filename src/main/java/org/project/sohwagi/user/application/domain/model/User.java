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

@Getter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  public void updateFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
  }

}
