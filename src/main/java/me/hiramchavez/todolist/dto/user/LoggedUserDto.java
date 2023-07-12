package me.hiramchavez.todolist.dto.user;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record LoggedUserDto(
  Long id,
  String firstName,
  String lastName,
  String email,
  String token

) implements Serializable {

}