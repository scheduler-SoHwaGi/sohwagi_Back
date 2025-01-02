package org.project.sohwagi.user.application.port.out;

public interface CheckRefreshTokenPort {

  boolean checkRefreshToken(Long userId, String refreshToken);

}
