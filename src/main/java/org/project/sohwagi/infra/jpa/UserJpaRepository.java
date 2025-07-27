package org.project.sohwagi.infra.jpa;

import java.util.Optional;
import org.project.sohwagi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByFcmToken(String fcmToken);

	Optional<User> findByUserName(String userName);

}
