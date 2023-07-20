package me.hiramchavez.todolist.dto;

public record ResponseDeleteDto(
  Boolean error,
    String message
) {
}
