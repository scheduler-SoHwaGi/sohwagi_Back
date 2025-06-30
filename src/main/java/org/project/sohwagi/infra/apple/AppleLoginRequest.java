package org.project.sohwagi.infra.apple;

import static org.project.sohwagi.common.validation.Validation.validate;

import jakarta.validation.constraints.NotNull;


public record AppleLoginRequest(
    @NotNull
    String authorizationCode,

    @NotNull
    String userName

    ) {
    public AppleLoginRequest(
        String authorizationCode,
        String userName
    ){
        this.authorizationCode = authorizationCode;
        this.userName = userName;
        validate(this);
    }
}
