package me.hiramchavez.todolist.controller;

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

import java.awt.print.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/list-tasks")
@RequiredArgsConstructor
public class ListTasksController {

    private final ListTasksService listTasksService;

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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDeleteDto> deleteListTasks(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(listTasksService.deleteListTask(id, request));
    }

}
