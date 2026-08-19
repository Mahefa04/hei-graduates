package school.hei.graduates.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException exception) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            Map.of(
                "status", 404,
                "message", exception.getMessage(),
                "timestamp", Instant.now().toString()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Map.of(
                "status", 400,
                "message", exception.getMessage(),
                "timestamp", Instant.now().toString()));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Map<String, Object>> handleConflict(ConflictException exception) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(
            Map.of(
                "status", 409,
                "message", exception.getMessage(),
                "timestamp", Instant.now().toString()));
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException exception) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            Map.of(
                "status", 403,
                "message", exception.getMessage(),
                "timestamp", Instant.now().toString()));
  }
}
