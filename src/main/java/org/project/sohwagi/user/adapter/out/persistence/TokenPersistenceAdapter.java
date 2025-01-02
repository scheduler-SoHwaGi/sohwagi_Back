package org.project.sohwagi.user.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.user.adapter.out.persistence.repository.TokenJpaRepository;
import org.project.sohwagi.user.application.domain.model.Token;
import org.project.sohwagi.user.application.port.out.CheckRefreshTokenPort;
import org.project.sohwagi.user.application.port.out.SaveRefreshTokenPort;

@PersistenceAdapter
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements CheckRefreshTokenPort, SaveRefreshTokenPort {

  private final TokenJpaRepository tokenJpaRepository;

  @Override
  public boolean checkRefreshToken(Long userId, String refreshToken) {
    return tokenJpaRepository.existsByUserIdAndRefreshToken(userId, refreshToken);
  }

  @Override
  public Token saveRefreshToken(Token token) {
    return tokenJpaRepository.save(token);
  }
}
