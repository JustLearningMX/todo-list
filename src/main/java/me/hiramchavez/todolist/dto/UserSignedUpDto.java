package me.hiramchavez.todolist.dto;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record UserSignedUpDto(
      Long id,
      String firstName,
      String lastName,
      String email,
      Boolean active

) implements Serializable {
}