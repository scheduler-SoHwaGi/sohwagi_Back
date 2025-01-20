package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.UserEntity;

public interface DeleteUserPort {

  void deleteUser(UserEntity userEntity);

}
