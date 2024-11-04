package org.project.sohwagi.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.user.entity.User;
import org.project.sohwagi.user.repository.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findUserByFcmToken(String fcmToken) {
		return userJpaRepository.findByFcmToken(fcmToken);
	}

	@Override
	@Transactional
	public void login(String fcmToken) {
		Optional<User> savedUser = findUserByFcmToken(fcmToken);

		if(savedUser.isEmpty()){
			User user = new User(fcmToken);
			userJpaRepository.save(user);
		}
	}
}
