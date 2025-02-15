package org.project.sohwagi.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionDto {

  private int statusCode;
  private HttpStatus state;
  private String message;
  private String newAccessToken;
}

