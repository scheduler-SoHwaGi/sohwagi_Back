package org.project.sohwagi.user;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.dto.service.SaveFcmTokenCommand;
import org.project.sohwagi.user.dto.service.GetOrCreateUserCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void saveFcmToken(SaveFcmTokenCommand command) {
    command.user().updateFcmToken(command.fcmToken());

    userRepository.update(command.user());
  }

  @Transactional
  public void deleteUser(User user) {

    userRepository.delete(user);
  }

  public User getOrCreateUser(GetOrCreateUserCommand getOrCreateUserCommand) {

    return userRepository.loadUserByOAuthProviderAndOAuthSubject(getOrCreateUserCommand);
  }

  public User loadUserById(Long id){
    return userRepository.findById(id);
  }
}
