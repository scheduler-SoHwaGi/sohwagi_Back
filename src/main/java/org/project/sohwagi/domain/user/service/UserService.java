package org.project.sohwagi.domain.user.service;

import java.util.Optional;
import org.project.sohwagi.domain.user.entity.User;

public interface UserService {

	Optional<User> findUserByFcmToken(String fcmToken);

	void login(String fcmToken);
}
