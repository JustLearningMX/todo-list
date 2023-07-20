package me.hiramchavez.todolist.exception.listtasks;

import jakarta.servlet.http.HttpServletRequest;
import me.hiramchavez.todolist.exception.ApplicationExceptionResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(3)
public class TaskExceptionHandler {

    @ExceptionHandler(ListTasksEmptyException.class)
    public ResponseEntity<ApplicationExceptionResponse> medicoNotFoundException(ListTasksEmptyException ex, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>(Map.of(ex.getClass().getSimpleName(), ex.getMessage()));
        ApplicationExceptionResponse errorResponse = createResponse(HttpStatus.BAD_REQUEST, req, errors);

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ListTasksNotFoundException.class)
    public ResponseEntity<ApplicationExceptionResponse> medicoNotFoundException(ListTasksNotFoundException ex, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>(Map.of(ex.getClass().getSimpleName(), ex.getMessage()));
        ApplicationExceptionResponse errorResponse = createResponse(HttpStatus.NOT_FOUND, req, errors);

        return ResponseEntity.status(404).body(errorResponse);
    }

    private static ApplicationExceptionResponse createResponse(
      HttpStatus status,
      HttpServletRequest req,
      Map<String, String> errors)
    {
        return new ApplicationExceptionResponse(
          true,
          ZonedDateTime.now(),
          status.value(),
          req.getRequestURI(),
          errors
        );
    }
}
