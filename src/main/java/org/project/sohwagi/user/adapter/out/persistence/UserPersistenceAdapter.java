package org.project.sohwagi.user.adapter.out.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.PersistenceAdapter;
import org.project.sohwagi.user.adapter.out.persistence.repository.UserJpaRepository;
import org.project.sohwagi.user.application.domain.model.User;
import org.project.sohwagi.user.application.port.out.LoadUserPort;
import org.project.sohwagi.user.application.port.out.SaveUserPort;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> loadUserByUserName(String userName) {
		return userJpaRepository.findByUserName(userName);
	}

	@Override
	public User loadUserById(Long userId) {
		return userJpaRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
	}

	public Optional<User> loadUserByOAuthProviderAndOAuthSubject(String provider, String subject) {
		return userJpaRepository.findByOauthProviderAndOauthSubject(provider, subject);
	}

	@Override
	public User saveUser(User user) {
		return userJpaRepository.save(user);
	}
}
