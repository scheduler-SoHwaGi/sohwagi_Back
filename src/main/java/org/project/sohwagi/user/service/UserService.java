package org.project.sohwagi.user.service;

import java.util.Optional;
import org.project.sohwagi.user.entity.User;

public interface UserService {

	Optional<User> findUserByFcmToken(String fcmToken);

	void login(String fcmToken);
}
