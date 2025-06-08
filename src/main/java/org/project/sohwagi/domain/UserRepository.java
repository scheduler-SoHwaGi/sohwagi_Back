package org.project.sohwagi.domain;

import jakarta.persistence.EntityNotFoundException;
import org.project.sohwagi.application.cmd.GetOrCreateUserCommand;
import org.project.sohwagi.infra.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final UserJpaRepository userJpaRepository;

  public UserRepository(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  public User loadUserByOAuthProviderAndOAuthSubject(GetOrCreateUserCommand getOrCreateUserCommand) {

    return userJpaRepository.findByOauthProviderAndOauthSubject(
        getOrCreateUserCommand.oauthProvider(),
        getOrCreateUserCommand.subject()
    ).orElseGet(() -> userJpaRepository.save(
        User.builder()
            .userName(getOrCreateUserCommand.userName())
            .email(getOrCreateUserCommand.email())
            .oauthSubject(getOrCreateUserCommand.subject())
            .oauthProvider(getOrCreateUserCommand.oauthProvider())
            .appleRefreshToken(getOrCreateUserCommand.appleRefreshToken())
            .build()
    ));
  }

  public void delete(UserDetails userDetails) {
    User user = userJpaRepository.findById(userDetails.id())
        .orElseThrow(() -> new EntityNotFoundException("해당 유저는 존재하지 않습니다."));

    userJpaRepository.delete(user);
  }

  public void update(User user) {
    userJpaRepository.saveAndFlush(user);
  }

  public User findById(Long id) {

    return userJpaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("해당 유저는 존재하지 않습니다."));
  }
}
