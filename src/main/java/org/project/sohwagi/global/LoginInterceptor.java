package org.project.sohwagi.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.user.entity.User;
import org.project.sohwagi.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler)
		throws IOException {

		String fcmToken = request.getHeader("FcmToken");
		log.info("User FcmToken: " + fcmToken);

		try {
			if (fcmToken != null) {
				User user = userService.findUserByFcmToken(fcmToken)
					.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
				request.setAttribute("userId", user.getId());
				log.info("User Id: " + user.getId());
				return true;
			} else {
				throw new IllegalArgumentException("등록되지 않은 fcm token 입니다.");
			}
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setHeader("message", e.getMessage());
			return false;
		}
	}
}
