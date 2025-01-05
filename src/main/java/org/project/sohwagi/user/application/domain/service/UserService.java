package org.project.sohwagi.user.application.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.application.domain.model.Token;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.port.in.command.CreateUserByUserNameCommand;
import org.project.sohwagi.user.application.port.in.command.LogoutCommand;
import org.project.sohwagi.user.application.port.in.command.SaveFcmTokenCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.project.sohwagi.user.application.port.in.usecase.LogoutUseCase;
import org.project.sohwagi.user.application.port.in.usecase.SaveFcmTokenUseCase;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.LogoutPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, SaveFcmTokenUseCase, LogoutUseCase {
	private final LoadUserPort loadUserPort;
	private final SaveUserPort saveUserPort;
	private final LogoutPort logoutPort;

	@Override
	@Transactional
	public void createUserByNickName(CreateUserByUserNameCommand command) {
		Optional<User> savedUser = loadUserPort.loadUserByUserName(command.userName());

		if(savedUser.isEmpty()) {
			User user = User
				.builder()
				.userName(command.userName())
				.build();
			saveUserPort.saveUser(user);
		}
	}

	@Override
	@Transactional
	public void saveFcmToken(SaveFcmTokenCommand command) {
		command.user().updateFcmToken(command.fcmToken());
	}


	@Override
	@Transactional
	public void logout(LogoutCommand command) {
		Token token = logoutPort.loadToken(command.refreshToken());

		token.expireToken();

		logoutPort.update(token);
	}
}
