package me.hiramchavez.todolist.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link me.hiramchavez.todolist.model.User}
 */
public record UserToLoginDto(
    @NotBlank(message = "Email is required to login")
    String email,
    @NotBlank(message = "Password is required to login")
    String password

) implements Serializable {
}