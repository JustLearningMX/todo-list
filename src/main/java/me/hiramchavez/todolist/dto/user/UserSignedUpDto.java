package me.hiramchavez.todolist.dto.user;

import me.hiramchavez.todolist.dto.listTasks.ListTasksResDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record UserSignedUpDto(
      Long id,
      String firstName,
      String lastName,
      String email,
      Boolean active,
      String role
//      List<ListTasksResDto> listTasks

) implements Serializable {
}