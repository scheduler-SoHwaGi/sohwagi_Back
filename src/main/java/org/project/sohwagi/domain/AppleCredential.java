package org.project.sohwagi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "apple_credentials")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleCredential {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String oauthSubject;

  @Column
  private String appleRefreshToken;

  @Column
  private Long userId;

}
