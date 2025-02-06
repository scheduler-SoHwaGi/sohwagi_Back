package org.project.sohwagi.token;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.common.exception.RefreshTokenException;
import org.project.sohwagi.token.dto.service.RefreshTokenCommand;

@PersistenceAdapter
@RequiredArgsConstructor
public class TokenRepository {

  private final TokenJpaRepository tokenJpaRepository;

  public boolean checkRefreshToken(String refreshToken) {
    Token token = tokenJpaRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new RefreshTokenException("해당 RefreshToken이 DB 내 존재하지 않습니다."));

    return token.isExpired();
  }

  public Token save(Token token) {
    return tokenJpaRepository.save(token);
  }

  public Token findByRefreshToken(RefreshTokenCommand command) {
    return tokenJpaRepository.findByRefreshToken(command.refreshToken())
        .orElseThrow(() -> new RefreshTokenException("해당 RefreshToken이 DB 내 존재하지 않습니다."));

  }

  public void update(Token token) {
    tokenJpaRepository.saveAndFlush(token);
  }
}
