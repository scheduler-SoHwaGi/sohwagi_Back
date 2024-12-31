package org.project.sohwagi.user.application.port.out;

import java.util.Optional;
import org.project.sohwagi.user.application.domain.model.User;

public interface LoadUserPort {

	Optional<User> loadUserByUserName(String userName);

	User loadUserById(Long userId);

	Optional<User> loadUserByOAuthProviderAndOAuthSubject(String provider, String subject);

}
