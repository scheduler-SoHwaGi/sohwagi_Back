package org.project.sohwagi.user.application.port.out;

import java.util.Optional;
import org.project.sohwagi.user.application.domain.model.UserEntity;
import org.project.sohwagi.user.application.domain.model.User;

public interface LoadUserPort {

	Optional<UserEntity> loadUserByUserName(String userName);

	User loadUserById(Long userId);

	Optional<UserEntity> loadUserByOAuthProviderAndOAuthSubject(String provider, String subject);

}
