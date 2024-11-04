package org.project.sohwagi.user.repository;

import java.util.Optional;
import org.project.sohwagi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByFcmToken(String fcmToken);

}
