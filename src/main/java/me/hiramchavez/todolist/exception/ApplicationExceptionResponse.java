package me.hiramchavez.todolist.exception;

import java.time.ZonedDateTime;
import java.util.Map;

public record ApplicationExceptionResponse(
      Boolean isError,
      ZonedDateTime timestamp,
      Integer statusCode,
      String path,
      Map<String, String> messages
) {
}
