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
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.dto.listTasks.ListTasksReqDto;
import me.hiramchavez.todolist.dto.listTasks.ListTasksResDto;
import me.hiramchavez.todolist.service.ListTasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "List of Tasks", description = "Manage all endpoints about List of Tasks")
@RestController
@RequestMapping("/list-tasks")
@RequiredArgsConstructor
public class ListTasksController {

    private final ListTasksService listTasksService;

    @Operation(
      summary = "Create a List of Tasks. Token is required.",
      description = "Let a user create a list of tasks with the user's token."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "List of tasks created successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ListTasksResDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ListTasksResDto> createListTask(
      @RequestBody @Valid ListTasksReqDto listTasksReqDto,
      HttpServletRequest request,
      UriComponentsBuilder uriComponentsBuilder
    ) {

        ListTasksResDto listTasksResDto = listTasksService.createListTask(listTasksReqDto, request);

        URI location = uriComponentsBuilder
          .path("/list-tasks/{id}")
          .buildAndExpand(listTasksResDto.id())
          .toUri();

        return ResponseEntity
          .created(location)
          .body(listTasksResDto);
    }

    @Operation(
      summary = "Update a List of Tasks. Token is required.",
      description = "Let a user update total o partially a list of tasks with the user's token."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "List of tasks updated successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ListTasksResDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "List of Tasks Not Found", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListTasksResDto> updateListTask(
      @PathVariable Long id,
      @RequestBody @Valid ListTasksReqDto listTasksReqDto,
      HttpServletRequest request
    ) {
        return ResponseEntity
          .status(200)
          .body(listTasksService.updateListTask(id, listTasksReqDto, request));
    }


    @Operation(
      summary = "Delete a List of Tasks. Token is required.",
      description = "Let a user delete a list of tasks with the user's token."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "List of tasks deleted successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDto.class))
        }),
      @ApiResponse(responseCode = "403", description = "Forbidden access to this resource", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found \t\n\n List of Tasks Not Found", content = {@Content})
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDeleteDto> deleteListTasks(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(listTasksService.deleteListTask(id, request));
    }
}
