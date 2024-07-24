package project.my.sbc2024.controller.exception;

import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleBusinessException(IllegalArgumentException businessException) {
    return new ResponseEntity<>(businessException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); // ERRO 422
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNotFoundException(NoSuchElementException notFoundException) {
    return new ResponseEntity<>("Resource ID not found", HttpStatus.NOT_FOUND); // ERRO 404
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
    var message = "Unexpected server error, see the logs.";
    logger.error(message, unexpectedException);
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR); // ERRO 500
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException badRequestException) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "Bad Request");
    errorResponse.put("message", badRequestException.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // ERRO 400
  }

}
