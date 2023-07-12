package me.hiramchavez.todolist.dto.listTasks;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.ListTasks}
 */
public record ListTasksReqDto(
      @NotBlank(message = "Name of the List Task is required")
      String name,
      String description,
      Boolean active

) implements Serializable {
}