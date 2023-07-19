package me.hiramchavez.todolist.dto.listTasks;

import jakarta.validation.constraints.NotBlank;
import me.hiramchavez.todolist.dto.task.TaskBodyResDto;
import me.hiramchavez.todolist.dto.task.TaskResponseDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link me.hiramchavez.todolist.model.ListTasks}
 */
public record ListTasksResDto(
    Long id,
    String name,
    String description,
    Boolean active,
    List<TaskBodyResDto> tasks

) implements Serializable {
}