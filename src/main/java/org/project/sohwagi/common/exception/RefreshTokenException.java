package org.project.sohwagi.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenException extends RuntimeException{

  public RefreshTokenException(String message) {
    super(message);
  }

}
