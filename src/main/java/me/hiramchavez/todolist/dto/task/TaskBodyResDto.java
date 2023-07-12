package me.hiramchavez.todolist.dto.task;

import me.hiramchavez.todolist.model.Priority;
import me.hiramchavez.todolist.model.State;

import java.io.Serializable;
import java.util.Date;

public record TaskBodyResDto(
  Long id,
  String title,
  String description,
  Date expirationDate,
  State state,
  Priority priority

) implements Serializable {
}