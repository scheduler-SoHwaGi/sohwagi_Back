package org.project.sohwagi.common.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    return createResponse(HttpStatus.BAD_REQUEST,
        e.getBindingResult().getFieldError().getDefaultMessage());
  }

  @ExceptionHandler({
      NoSuchElementException.class,
      EntityNotFoundException.class
  })
  public ResponseEntity<ExceptionDto> handleBadRequestException(Exception e) {
    return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException e) {
    return createResponse(HttpStatus.CONFLICT, e.getMessage());
  }

  @ExceptionHandler({
      JwtException.class,
      AccessDeniedException.class
  })
  public ResponseEntity<ExceptionDto> handleJwtException(Exception e) {
    return createResponse(HttpStatus.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler(OAuthRequestException.class)
  public ResponseEntity<ExceptionDto> handleOAuthException(OAuthRequestException e) {
    return createResponse(e.getStatus(), e.getMessage());
  }

  @ExceptionHandler(RefreshTokenException.class)
  public ResponseEntity<ExceptionDto> handleRefreshTokenException(Exception e) {
    return createResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler(
      AccessTokenException.class
  )
  public ResponseEntity<ExceptionDto> handleAccessTokenException(AccessTokenException e) {
    return createAccessTokenResponse(HttpStatus.FORBIDDEN, e.getMessage(), e.getNewAccessToken());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Map<String,String>> handleMissingParam(MissingServletRequestParameterException ex) {
    String name = ex.getParameterName();
    return ResponseEntity
        .badRequest()
        .body(Map.of("error", String.format("'%s' 파라미터는 필수입니다.", name)));
  }

  private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message) {
    return ResponseEntity.status(status.value())
        .body(ExceptionDto.builder()
            .statusCode(status.value())
            .state(status)
            .message(message)
            .build());
  }

  private ResponseEntity<ExceptionDto> createAccessTokenResponse(HttpStatus status, String message, String newAccessToken){
    return ResponseEntity.status(status.value())
        .body(ExceptionDto.builder()
            .statusCode(status.value())
            .state(status)
            .message(message)
            .newAccessToken(newAccessToken)
            .build());
  }
}

