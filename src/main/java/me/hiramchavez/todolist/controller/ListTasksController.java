package me.hiramchavez.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ListTasksReqDto;
import me.hiramchavez.todolist.dto.ListTasksResDto;
import me.hiramchavez.todolist.service.ListTasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/list-tasks")
@RequiredArgsConstructor
public class ListTasksController {

    private final ListTasksService listTasksService;

    @PostMapping("/create")
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
}
