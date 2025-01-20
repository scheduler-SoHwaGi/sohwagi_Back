package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.TokenEntity;

public interface SaveRefreshTokenPort {

  TokenEntity saveRefreshToken(TokenEntity tokenEntity);

}
