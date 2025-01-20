package org.project.sohwagi.user;

import jakarta.persistence.EntityNotFoundException;
import org.project.sohwagi.user.dto.service.GetOrCreateUserCommand;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final UserJpaRepository userJpaRepository;

  public UserRepository(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  public User loadUserByOAuthProviderAndOAuthSubject(GetOrCreateUserCommand getOrCreateUserCommand) {
    UserEntity userEntity = userJpaRepository.findByOauthProviderAndOauthSubject(
        getOrCreateUserCommand.oauthProvider(),
        getOrCreateUserCommand.subject()
    ).orElseGet(() -> userJpaRepository.save(
        UserEntity.builder()
            .userName(getOrCreateUserCommand.userName())
            .email(getOrCreateUserCommand.email())
            .oauthSubject(getOrCreateUserCommand.subject())
            .oauthProvider(getOrCreateUserCommand.oauthProvider())
            .appleRefreshToken(getOrCreateUserCommand.appleRefreshToken())
            .build()
    ));

    return User.from(userEntity);
  }

  public void delete(User user) {
    UserEntity userEntity = userJpaRepository.findById(user.getId())
        .orElseThrow(() -> new EntityNotFoundException("해당 유저도는 존재하지 않습니다."));

    userJpaRepository.delete(userEntity);
  }

  public void update(User user) {
    userJpaRepository.saveAndFlush(user.toEntity());
  }

  public User findById(Long id) {
    UserEntity userEntity = userJpaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("해당 유저도는 존재하지 않습니다."));

    return User.from(userEntity);
  }
}
