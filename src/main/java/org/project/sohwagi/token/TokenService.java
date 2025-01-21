package org.project.sohwagi.token;

import org.project.sohwagi.token.dto.service.RefreshTokenCommand;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private TokenRepository tokenRepository;

  public TokenService(TokenRepository tokenRepository){
    this.tokenRepository = tokenRepository;
  }

  public String saveToken(RefreshTokenCommand command){
    Token token = Token.builder()
        .refreshToken(command.refreshToken())
        .isExpired(false)
        .build();

    Token savedToken = tokenRepository.save(token);

    return savedToken.getRefreshToken();
  }

  public boolean checkToken(String refreshToken) {
    return tokenRepository.checkRefreshToken(refreshToken);
  }

  public void expireToken(RefreshTokenCommand command){
    Token token = tokenRepository.findByRefreshToken(command);

    token.expireToken();

    tokenRepository.update(token);
  }

}
