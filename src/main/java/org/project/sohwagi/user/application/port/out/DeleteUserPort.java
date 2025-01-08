package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.application.domain.model.User;

public interface DeleteUserPort {

  void deleteUser(User user);

}
