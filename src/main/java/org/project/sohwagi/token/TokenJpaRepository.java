package org.project.sohwagi.token;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

  boolean existsByRefreshToken (String refreshToken);

  Optional<Token> findByRefreshToken(String token);

}
