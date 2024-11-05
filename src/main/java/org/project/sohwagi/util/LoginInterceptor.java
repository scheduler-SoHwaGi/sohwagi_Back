package org.project.sohwagi.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.domain.service.UserService;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final LoadUserPort loadUserPort;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler)
		throws IOException {
		log.info("Request URI: " + request.getRequestURI());

		String nickName = request.getHeader("NickName");
		log.info("User NickName: " + nickName);

		try {
			if (nickName != null) {
				User user = loadUserPort.loadUserByNickName(nickName)
					.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
				request.setAttribute("userId", user.getId());
				log.info("User Id: " + user.getId());
				return true;
			} else {
				throw new IllegalArgumentException("등록되지 않은 nickName 입니다.");
			}
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setHeader("message", e.getMessage());
			return false;
		}
	}
}
