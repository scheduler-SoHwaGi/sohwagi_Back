package org.project.sohwagi.domain.user.repository;

import java.util.Optional;
import org.project.sohwagi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByFcmToken(String fcmToken);

}
