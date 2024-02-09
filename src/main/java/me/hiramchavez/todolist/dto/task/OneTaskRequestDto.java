package me.hiramchavez.todolist.dto.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.Task}
 */
public record OneTaskRequestDto(

  @NotNull(message = "List list-tasks id is required")
  Long list_task_id,

  @Valid
  TaskBodyReqDto task

) implements Serializable {
}