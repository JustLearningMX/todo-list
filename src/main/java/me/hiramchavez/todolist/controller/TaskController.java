package me.hiramchavez.todolist.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.task.ListTaskReqDto;
import me.hiramchavez.todolist.dto.task.TaskRequestDto;
import me.hiramchavez.todolist.dto.task.TaskRequestPutDto;
import me.hiramchavez.todolist.dto.task.TaskResponseDto;
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Manage all endpoints about Tasks")
public class TaskController {

    private final TaskService taskService;

    /*Create one o more tasks into a list*/
    @PostMapping
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

    @PutMapping
    @Transactional
    public ResponseEntity<TaskResponseDto> updateTask(
      @RequestBody @Valid TaskRequestPutDto taskRequestPutDto,
      HttpServletRequest request) {

        return ResponseEntity
          .status(200)
          .body(taskService.updateTasks(taskRequestPutDto, request));
    }

    /*Delete one o more tasks from a list*/
    @DeleteMapping
    @Transactional
    public ResponseEntity<ResponseDeleteDto> deleteTasks(
      @RequestBody @Valid ListTaskReqDto listTaskReqDto,
      HttpServletRequest request) {

        return ResponseEntity
          .status(200)
          .body(taskService.deleteTasks(listTaskReqDto, request));
    }
}
