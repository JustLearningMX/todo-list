package me.hiramchavez.todolist.dto.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link me.hiramchavez.todolist.model.Task}
 */
public record TaskRequestDto(
      @NotNull(message = "List listtasks id is required")
      Long list_task_id,
      @Valid
      List<TaskBodyReqDto> tasks

) implements Serializable {
}