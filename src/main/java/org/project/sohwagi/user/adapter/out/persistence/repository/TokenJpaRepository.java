package org.project.sohwagi.user.adapter.out.persistence.repository;

import java.util.Optional;
import org.project.sohwagi.user.application.domain.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

  boolean existsByUserEntity_IdAndRefreshToken (Long userId, String refreshToken);

  Optional<TokenEntity> findByRefreshToken(String token);

}
