package org.project.sohwagi.user.adapter.out.persistence.repository;

import org.project.sohwagi.user.application.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

  boolean existsByUserIdAndRefreshToken (Long userId, String refreshToken);

}
