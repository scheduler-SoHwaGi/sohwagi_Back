package org.project.sohwagi.user.application.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.port.in.command.CreateUserByNickNameCommand;
import org.project.sohwagi.user.application.port.in.usecase.CreateUserUseCase;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase {
	private final LoadUserPort loadUserPort;
	private final SaveUserPort saveUserPort;

	@Override
	@Transactional
	public void createUserByNickName(CreateUserByNickNameCommand command) {
		Optional<User> savedUser = loadUserPort.loadUserByNickName(command.nickName());

		if(savedUser.isEmpty()) {
			User user = User
				.builder()
				.nickName(command.nickName())
				.build();
			saveUserPort.saveUser(user);
		}
	}
}
