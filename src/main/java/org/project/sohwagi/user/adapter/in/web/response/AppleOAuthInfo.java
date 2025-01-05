package org.project.sohwagi.user.adapter.in.web.response;

import static org.project.sohwagi.common.validation.Validation.validate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record AppleOAuthInfo(
    @JsonProperty("sub")
    String subject,

    @JsonProperty("email")
    String email,

    @JsonProperty("refreshToken")
    String refreshToken

) {

}
