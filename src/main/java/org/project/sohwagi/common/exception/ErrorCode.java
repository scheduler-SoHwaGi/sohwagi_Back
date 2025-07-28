package org.project.sohwagi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_SCHEDULE_TEXT_INPUT_VALUE(
      HttpStatus.BAD_REQUEST, "E400001", "일정 내용을 작성해주세요."),
  INTERNAL_LLM_SERVER_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR, "E500002", "잠시후에 다시 시도해주세요");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
