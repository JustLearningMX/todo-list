package me.hiramchavez.todolist.dto.task;

import me.hiramchavez.todolist.model.Priority;
import me.hiramchavez.todolist.model.State;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link me.hiramchavez.todolist.model.Task}
 */
public record TaskResponseDto(
    Long userId,
    Long ListTaskId,
    List<TaskBodyResDto> tasks

) implements Serializable {
}