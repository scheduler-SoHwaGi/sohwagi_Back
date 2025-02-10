package org.project.sohwagi.util;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.project.sohwagi.common.TokenValidationResult;
import org.project.sohwagi.common.exception.RefreshTokenException;
import org.project.sohwagi.token.TokenService;
import org.project.sohwagi.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

  private final JwtUtil jwtUtil;
  private final TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    log.info(request.getRequestURI());
    String accessToken = request.getHeader("X-ACCESS-TOKEN");
    String refreshToken = request.getHeader("X-REFRESH-TOKEN");
    log.info("Prehandle 시작 : "+refreshToken);

    if (accessToken == null || refreshToken == null) {
      throw new JwtException("Missing access or refresh token");
    }

    TokenValidationResult accessTokenResult = jwtUtil.validateToken(accessToken, true);
    if (accessTokenResult == TokenValidationResult.VALID) {
      log.info("ok");
      request.setAttribute("isAccessToken", true);
      return true;
    }

    try {
      if (accessTokenResult == TokenValidationResult.EXPIRED){
        log.info("access-token-state : "+accessTokenResult.toString());
        log.error("엑세스 토큰 만료. 에러 던집니다.");

        throw new JwtException("엑세스 토큰 만료");
      }
    } catch (JwtException e){
      log.error("JwtException 감지");

      TokenValidationResult refreshTokenResult = jwtUtil.validateToken(refreshToken, false);
      log.info(refreshTokenResult.toString());

      if (refreshTokenResult == TokenValidationResult.VALID) {
        log.info("getUserInfo 전");
        String substringToken = jwtUtil.substringToken(refreshToken);
        Long userId = jwtUtil.getUserInfoFromToken(substringToken, false);
        log.info("getUserInfo 후");


        if (!tokenService.checkToken(refreshToken)) { // Port로 DB 검증
          log.info("토큰 검증완료");
          String newAccessToken = jwtUtil.createAccessToken(userId);
          response.setHeader("NEW-ACCESS-TOKEN", newAccessToken); // api 요청 결과와 함께 응답 헤더에 담김
          request.setAttribute("isAccessToken", false);

        } else {
          throw new RefreshTokenException("RefreshToken 만료되었습니다. 로그인 필요합니다.");
        }
      }

      throw e;
    }

    throw new JwtException("Invalid tokens");
  }
}
