package org.project.sohwagi.user.application.domain.model;

public record User(
    String fcmToken,
    String name,
    String email
) {

}
