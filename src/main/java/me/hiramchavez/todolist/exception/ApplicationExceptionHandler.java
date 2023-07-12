package me.hiramchavez.todolist.exception;

import jakarta.servlet.http.HttpServletRequest;
import me.hiramchavez.todolist.exception.user.UserExceptionResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(2)
public class ApplicationExceptionHandler {

    // General Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserExceptionResponse> genericException(Exception ex, HttpServletRequest req) {
        System.out.println("ApplicationExceptionHandler.genericException");
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