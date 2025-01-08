package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.Token;

public interface LogoutPort {

  Token loadToken(String refreshToken);

  void update (Token token);

}
