package org.project.sohwagi.user.adapter.out.persistence.repository;

import java.util.Optional;
import org.project.sohwagi.user.application.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByFcmToken(String fcmToken);

	Optional<User> findByNickName(String nickName);

}
