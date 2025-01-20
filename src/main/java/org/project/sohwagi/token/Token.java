package org.project.sohwagi.token;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

  private Long id;

  private String refreshToken;

  private boolean isExpired;

  public TokenEntity toEntity(){
    return new TokenEntity(
        id,
        refreshToken,
        isExpired
    );
  }

  public static Token from(TokenEntity tokenEntity){
    return new Token(
        tokenEntity.getId(),
        tokenEntity.getRefreshToken(),
        tokenEntity.isExpired()
    );
  }

  public void expireToken(){
    this.isExpired = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Token token)) {
      return false;
    }
    return isExpired == token.isExpired && Objects.equals(id, token.id)
        && Objects.equals(refreshToken, token.refreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, refreshToken, isExpired);
  }
}
