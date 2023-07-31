package me.hiramchavez.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.listTasks.ListTasksResDto;
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
    @Operation(
      summary = "Create Tasks of a list. Token is required.",
      description = "Let a user create create one or more tasks into a list with the user's token."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Tasks created successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskResponseDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found.\t\n\n List of Tasks Not Found.\t\n\n List of Tasks received are empty.", content = {@Content})
    })
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

    @Operation(
      summary = "Update Tasks of a list. Token is required.",
      description = "Let a user update one or more tasks into a list with the user's token."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Tasks updated successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskResponseDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found.\t\n\n List of Tasks Not Found.\t\n\n List of Tasks received are empty.", content = {@Content})
    })
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
    @Operation(
      summary = "Delete Tasks of a list. Token is required.",
      description = "Let a logged user delete one or more tasks from a list."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Tasks deleted successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found.\t\n\n List of Tasks Not Found.\t\n\n List of Tasks received are empty.", content = {@Content})
    })
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
