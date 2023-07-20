package me.hiramchavez.todolist.dto.task;

import java.util.List;

public record ListTaskReqDto(
  List<Long> listId
) {
}
