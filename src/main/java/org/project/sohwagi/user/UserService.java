package org.project.sohwagi.user;

import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.user.dto.res.GetUserInfoRes;
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
    User user = command.userDetails().toEntity();
    user.updateFcmToken(command.fcmToken());

    userRepository.update(user);
  }

  @Transactional
  public void deleteUser(UserDetails userDetails) {

    userRepository.delete(userDetails);
  }

  public Long getOrCreateUser(GetOrCreateUserCommand getOrCreateUserCommand) {
    User user = userRepository.loadUserByOAuthProviderAndOAuthSubject(getOrCreateUserCommand);

    if(user.isDeleted()){
      user.reLogin();

      userRepository.update(user);
    }

    return user.getId();
  }

  public UserDetails loadUserById(Long id) {

    User user = userRepository.findById(id);

    return UserDetails.from(user);
  }

  public GetUserInfoRes getUserInfo(UserDetails userDetails){
    String name = convertName(userDetails.userName());

    return new GetUserInfoRes(name, userDetails.email());
  }

  private String convertName(String name) {
    if (name.contains(" ")) {
      String[] parts = name.split(" ");
      if (parts.length == 2) {
        String firstName = parts[0];
        String lastName = parts[1];

        return lastName + firstName;
      }
    }
    return name;
  }
}
