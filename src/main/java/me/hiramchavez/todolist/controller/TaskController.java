package me.hiramchavez.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.task.TaskRequestDto;
import me.hiramchavez.todolist.dto.task.TaskResponseDto;
import me.hiramchavez.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /*Create one o more tasks into a list*/
    @PostMapping
    @RequestMapping("/create")
    @Transactional
    public ResponseEntity<TaskResponseDto> createTasks(
      @RequestBody @Valid TaskRequestDto taskRequestDto,
      HttpServletRequest request,
      UriComponentsBuilder uriComponentsBuilder) {

        TaskResponseDto taskResponseDto = taskService.createTasks(taskRequestDto, request);

        URI location = uriComponentsBuilder
          .path("/user/{id}/list-tasks/{id}")
          .buildAndExpand(taskResponseDto.userId(), taskResponseDto.ListTaskId())
          .toUri();

        return ResponseEntity
          .created(location)
          .body(taskResponseDto);
    }
}
