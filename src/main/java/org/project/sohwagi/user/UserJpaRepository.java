package org.project.sohwagi.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByFcmToken(String fcmToken);

	Optional<User> findByUserName(String userName);

	Optional<User> findByOauthProviderAndOauthSubject(String provider, String subject);


}
