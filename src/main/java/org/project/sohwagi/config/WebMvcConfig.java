package org.project.sohwagi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.util.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		log.info("인터셉터 등록");
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/api/v1/users/log-in/nicknames")
				.excludePathPatterns("OPTIONS/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("Nickname", "Content-Type", "Authorization")
				.allowCredentials(true);
	}
}
