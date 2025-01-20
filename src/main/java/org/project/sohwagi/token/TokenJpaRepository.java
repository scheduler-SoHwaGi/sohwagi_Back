package org.project.sohwagi.token;

import java.util.Optional;
import org.project.sohwagi.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

  boolean existsByRefreshToken (String refreshToken);

  Optional<TokenEntity> findByRefreshToken(String token);

}
