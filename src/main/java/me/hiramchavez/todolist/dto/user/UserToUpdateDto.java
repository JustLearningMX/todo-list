package me.hiramchavez.todolist.dto.user;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record UserToUpdateDto(
  String firstName,
  String lastName

) implements Serializable {
}