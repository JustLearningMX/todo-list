package me.hiramchavez.todolist.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record UserToSignUpDto(
      @Size
      @NotBlank(message = "First name is required in order to sign up")
      String firstName,
      @NotBlank(message = "Last name is required in order to sign up")
      String lastName,
      @Email(message = "Email must be a valid one ")
      @NotBlank(message = "Email is required in order to sign up")
      String email,
      @NotBlank(message = "Password is required in order to sign up")
      String password,
      @NotNull(message = "Set user as active")
      Boolean active

) implements Serializable {
}