package org.project.sohwagi.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.user.adapter.in.web.request.UserNickNameRequest;
import org.project.sohwagi.user.application.port.in.command.CreateUserByNickNameCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final CreateUserUseCase createUserUseCase;

	@PostMapping("/log-in/nicknames")
	public ResponseEntity<String> login(@RequestBody UserNickNameRequest request) {
		CreateUserByNickNameCommand command = CreateUserByNickNameCommand
			.builder()
			.nickName(request.getNickName())
			.build();

		createUserUseCase.createUserByNickName(command);

		return ResponseEntity.ok().build();
	}
}
