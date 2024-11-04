package org.project.sohwagi.user.controller;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.user.dto.UserFcmTokenRequest;
import org.project.sohwagi.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/log-in")
	public ResponseEntity<String> login(@RequestBody UserFcmTokenRequest request) {
		userService.login(request.getFcmToken());

		return ResponseEntity.ok().build();
	}
}
