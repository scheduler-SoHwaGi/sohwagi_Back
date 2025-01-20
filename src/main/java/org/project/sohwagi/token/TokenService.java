package org.project.sohwagi.token;

import org.project.sohwagi.token.dto.service.RefreshTokenCommand;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private TokenRepository tokenRepository;

  public TokenService(TokenRepository tokenRepository){
    this.tokenRepository = tokenRepository;
  }

  public Token saveToken(RefreshTokenCommand command){
    return tokenRepository.save(command);
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
