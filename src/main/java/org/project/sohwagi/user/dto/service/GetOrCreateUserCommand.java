package org.project.sohwagi.user.dto.service;

public record GetOrCreateUserCommand(
    String userName,
    String email,
    String oauthProvider,
    String subject,
    String appleRefreshToken
) {

}
