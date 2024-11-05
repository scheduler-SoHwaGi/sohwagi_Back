package org.project.sohwagi.user.application.port.out;

import java.util.Optional;
import org.project.sohwagi.user.application.domain.model.User;

public interface LoadUserPort {

	Optional<User> loadUserByNickName(String nickName);

}
