package org.project.sohwagi.user.adapter.in.interceptor;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.TokenValidationResult;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.domain.service.UserService;
import org.project.sohwagi.user.application.port.out.CheckRefreshTokenPort;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final LoadUserPort loadUserPort;
	private final JwtUtil jwtUtil;
	private final CheckRefreshTokenPort checkRefreshTokenPort;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) {
		log.info(request.getRequestURI());
		String accessToken = request.getHeader("X-ACCESS-TOKEN");
		String refreshToken = request.getHeader("X-REFRESH-TOKEN");

		if (accessToken == null || refreshToken == null) {
			throw new JwtException("Missing access or refresh token");
		}

		TokenValidationResult accessTokenResult = jwtUtil.validateToken(accessToken, true);
		if (accessTokenResult == TokenValidationResult.VALID) {
			log.info("ok");
			request.setAttribute("isAccessToken", true);
			return true;
		}

		if (accessTokenResult == TokenValidationResult.EXPIRED) {
			TokenValidationResult refreshTokenResult = jwtUtil.validateToken(refreshToken, false);
			if (refreshTokenResult == TokenValidationResult.VALID) {
				Long userId = jwtUtil.getUserInfoFromToken(refreshToken, false);

				if (checkRefreshTokenPort.checkRefreshToken(userId, refreshToken)) { // Port로 DB 검증
					String newAccessToken = jwtUtil.createAccessToken(userId);
					response.setHeader("New-Access-Token", newAccessToken); // api 요청 결과와 함께 응답 헤더에 담김
					request.setAttribute("isAccessToken", false);
					return true;
				} else {
					throw new JwtException("Invalid refresh token in database");
				}
			}
		}

		throw new JwtException("Invalid tokens");
	}
}
