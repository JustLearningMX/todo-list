package me.hiramchavez.todolist.exception.user;

import jakarta.servlet.http.HttpServletRequest;
import me.hiramchavez.todolist.exception.user.UserExceptionResponse;
import me.hiramchavez.todolist.exception.user.UserNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserExceptionResponse> medicoNotFoundException(UserNotFoundException ex, HttpServletRequest req) {
        System.out.println("UserExceptionHandler.UserNotFoundException");
        Map<String, String> errors = new HashMap<>(Map.of(ex.getClass().getSimpleName(), ex.getMessage()));
        UserExceptionResponse errorResponse = createResponse(HttpStatus.NOT_FOUND, req, errors);

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = ex.getFieldErrors().stream().collect(
          Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
        );

        UserExceptionResponse errorResponse = createResponse(HttpStatus.BAD_REQUEST, req, errors);
        return ResponseEntity.status(400).body(errorResponse);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UserExceptionResponse> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest req) {

        Map<String, String> errors = new HashMap<>(Map.of(ex.getClass().getSimpleName(), ex.getMessage()));
        UserExceptionResponse errorResponse = createResponse(HttpStatus.BAD_REQUEST, req, errors);
        return ResponseEntity.status(400).body(errorResponse);

    }

    private static UserExceptionResponse createResponse(
      HttpStatus status,
      HttpServletRequest req,
      Map<String, String> errors)
    {
        return new UserExceptionResponse(
          true,
          ZonedDateTime.now(),
          status.value(),
          req.getRequestURI(),
          errors
        );
    }
}
