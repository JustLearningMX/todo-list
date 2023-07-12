package me.hiramchavez.todolist.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.hiramchavez.todolist.model.Priority;
import me.hiramchavez.todolist.model.State;

import java.io.Serializable;
import java.util.Date;

public record TaskBodyReqDto(
  @NotBlank(message = "Title for task is required")
  String title,
  @NotBlank(message = "Description for task is required")
  String description,
  @NotNull(message = "Expiration date for task is required")
  Date expirationDate,
  @NotNull(message = "State for task is required")
  State state,
  @NotNull(message = "Priority for task is required")
  Priority priority

) implements Serializable {
}