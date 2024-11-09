package org.project.sohwagi.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.user.adapter.in.web.request.UserFcmTokenRequest;
import org.project.sohwagi.user.adapter.in.web.request.UserNickNameRequest;
import org.project.sohwagi.user.application.port.in.command.SaveFcmTokenCommand;
import org.project.sohwagi.user.application.port.in.command.CreateUserByNickNameCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.project.sohwagi.user.application.port.in.usecase.SaveFcmTokenUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final CreateUserUseCase createUserUseCase;
	private final SaveFcmTokenUseCase saveFcmTokenUseCase;

	@PostMapping("/log-in/nicknames")
	public ResponseEntity<String> login(@RequestBody UserNickNameRequest request) {
		CreateUserByNickNameCommand command = CreateUserByNickNameCommand
			.builder()
			.nickName(request.getNickName())
			.build();

		createUserUseCase.createUserByNickName(command);

		return ResponseEntity.ok().build();
	}
	@PatchMapping("/fcmTokens")
	public ResponseEntity<String> login(@RequestBody UserFcmTokenRequest request,
		@RequestAttribute("userId") Long userId) {
		SaveFcmTokenCommand command = SaveFcmTokenCommand
			.builder()
			.fcmToken(request.getFcmToken())
			.userId(userId)
			.build();

		saveFcmTokenUseCase.saveFcmToken(command);

		return ResponseEntity.ok().build();
	}
}
