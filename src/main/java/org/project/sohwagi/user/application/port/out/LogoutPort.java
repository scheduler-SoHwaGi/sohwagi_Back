package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.TokenEntity;

public interface LogoutPort {

  TokenEntity loadToken(String refreshToken);

  void update (TokenEntity tokenEntity);

}
