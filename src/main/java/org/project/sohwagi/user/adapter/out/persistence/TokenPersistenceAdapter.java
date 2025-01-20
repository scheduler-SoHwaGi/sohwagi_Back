package org.project.sohwagi.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.user.adapter.out.persistence.repository.TokenJpaRepository;
import org.project.sohwagi.user.application.domain.model.TokenEntity;
import org.project.sohwagi.user.application.port.out.CheckRefreshTokenPort;
import org.project.sohwagi.user.application.port.out.LogoutPort;
import org.project.sohwagi.user.application.port.out.SaveRefreshTokenPort;

@PersistenceAdapter
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements CheckRefreshTokenPort, SaveRefreshTokenPort,
    LogoutPort {

  private final TokenJpaRepository tokenJpaRepository;

  @Override
  public boolean checkRefreshToken(Long userId, String refreshToken) {
    return tokenJpaRepository.existsByUserEntity_IdAndRefreshToken(userId, refreshToken);
  }

  @Override
  public TokenEntity saveRefreshToken(TokenEntity tokenEntity) {
    return tokenJpaRepository.save(tokenEntity);
  }

  @Override
  public TokenEntity loadToken(String refreshToken) {
    return tokenJpaRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new EntityNotFoundException("리프레쉬 토큰이 존재하지 않습니다."));
  }

  @Override
  public void update(TokenEntity tokenEntity) {
    tokenJpaRepository.saveAndFlush(tokenEntity);
  }
}
