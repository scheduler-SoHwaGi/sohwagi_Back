package org.project.sohwagi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_SCHEDULE_TEXT_INPUT_VALUE(
      HttpStatus.BAD_REQUEST, "S001", "일정 내용을 작성해주세요."),
  INTERNAL_LLM_SERVER_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR, "S002", "잠시후에 다시 시도해주세요"),
  LLM_RESULT_PARSING_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR, "S002", "AI 서버 응답을 파싱하는 데 실패했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
