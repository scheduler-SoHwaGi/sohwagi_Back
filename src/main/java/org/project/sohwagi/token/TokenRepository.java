package org.project.sohwagi.token;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.token.dto.service.RefreshTokenCommand;

@PersistenceAdapter
@RequiredArgsConstructor
public class TokenRepository {

  private final TokenJpaRepository tokenJpaRepository;

  public boolean checkRefreshToken(String refreshToken) {
    return tokenJpaRepository.existsByRefreshToken(refreshToken);
  }

  public Token save(Token token) {
    return tokenJpaRepository.save(token);
  }

  public Token findByRefreshToken(RefreshTokenCommand command) {
    return tokenJpaRepository.findByRefreshToken(command.refreshToken())
        .orElseThrow(() -> new EntityNotFoundException("리프레쉬 토큰이 존재하지 않습니다."));

  }

  public void update(Token token) {
    tokenJpaRepository.saveAndFlush(token);
  }
}
