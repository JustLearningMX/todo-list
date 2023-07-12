package me.hiramchavez.todolist.exception.user;

import java.time.ZonedDateTime;
import java.util.Map;

public record UserExceptionResponse(
      Boolean isError,
      ZonedDateTime timestamp,
      Integer statusCode,
      String path,
      Map<String, String> messages
) {
}
