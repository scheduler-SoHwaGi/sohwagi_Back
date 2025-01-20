package org.project.sohwagi.user;

import java.util.Optional;
import org.project.sohwagi.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByFcmToken(String fcmToken);

	Optional<UserEntity> findByUserName(String userName);

	Optional<UserEntity> findByOauthProviderAndOauthSubject(String provider, String subject);


}
