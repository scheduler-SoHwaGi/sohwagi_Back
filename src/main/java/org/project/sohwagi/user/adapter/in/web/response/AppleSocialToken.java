package org.project.sohwagi.user.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleSocialToken(
    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Long expiresIn,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("id_token")
    String idToken
) {

}
