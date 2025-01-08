package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.adapter.in.web.response.AppleOAuthInfo;
import org.project.sohwagi.user.application.domain.model.User;

public interface ApplePort {

  AppleOAuthInfo getAppleOAuthInfo(String authorizationCode);

  void revoke(User user);

}
