package org.project.sohwagi.user.application.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.application.domain.model.TokenEntity;
import org.project.sohwagi.user.application.domain.model.UserEntity;
import org.project.sohwagi.user.application.port.in.command.CreateUserByUserNameCommand;
import org.project.sohwagi.user.application.port.in.command.DeleteUserCommand;
import org.project.sohwagi.user.application.port.in.command.LogoutCommand;
import org.project.sohwagi.user.application.port.in.command.SaveFcmTokenCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.project.sohwagi.user.application.port.in.usecase.DeleteUserUseCase;
import org.project.sohwagi.user.application.port.in.usecase.LogoutUseCase;
import org.project.sohwagi.user.application.port.in.usecase.SaveFcmTokenUseCase;
import org.project.sohwagi.user.application.port.out.DeleteUserPort;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.LogoutPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, SaveFcmTokenUseCase, LogoutUseCase,
		DeleteUserUseCase {
	private final LoadUserPort loadUserPort;
	private final SaveUserPort saveUserPort;
	private final LogoutPort logoutPort;
	private final DeleteUserPort deleteUserPort;

	@Override
	@Transactional
	public void createUserByNickName(CreateUserByUserNameCommand command) {
		Optional<UserEntity> savedUser = loadUserPort.loadUserByUserName(command.userName());

		if(savedUser.isEmpty()) {
			UserEntity userEntity = UserEntity
				.builder()
				.userName(command.userName())
				.build();
			saveUserPort.saveUser(userEntity);
		}
	}

	@Override
	@Transactional
	public void saveFcmToken(SaveFcmTokenCommand command) {
		command.userEntity().updateFcmToken(command.fcmToken());
	}


	@Override
	@Transactional
	public void logout(LogoutCommand command) {
		TokenEntity tokenEntity = logoutPort.loadToken(command.refreshToken());

		tokenEntity.expireToken();

		logoutPort.update(tokenEntity);
	}

	@Override
	@Transactional
	public void deleteUser(DeleteUserCommand command) {
		TokenEntity tokenEntity = logoutPort.loadToken(command.refreshToken());

		tokenEntity.expireToken();

		deleteUserPort.deleteUser(command.userEntity());
		logoutPort.update(tokenEntity);
	}
}
