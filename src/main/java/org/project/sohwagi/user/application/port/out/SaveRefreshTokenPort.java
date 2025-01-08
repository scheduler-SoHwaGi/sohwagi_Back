package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.Token;

public interface SaveRefreshTokenPort {

  Token saveRefreshToken(Token token);

}
