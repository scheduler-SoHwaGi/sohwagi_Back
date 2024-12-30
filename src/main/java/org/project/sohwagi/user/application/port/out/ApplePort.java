package org.project.sohwagi.user.application.port.out;

import org.project.sohwagi.user.adapter.in.web.response.AppleOAuthInfo;

public interface ApplePort {

  AppleOAuthInfo getAppleOAuthInfo(String authorizationCode);

}
