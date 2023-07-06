package me.hiramchavez.todolist.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.LoggedUserDto;
import me.hiramchavez.todolist.dto.UserSignedUpDto;
import me.hiramchavez.todolist.dto.UserToLoginDto;
import me.hiramchavez.todolist.dto.UserToSignUpDto;
import me.hiramchavez.todolist.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<UserSignedUpDto> signUp(@RequestBody @Valid UserToSignUpDto userToSignUpDto, UriComponentsBuilder uriComponentsBuilder) {

        UserSignedUpDto userSignedUpDto = userService.signUp(userToSignUpDto);

        URI location = uriComponentsBuilder
          .path("/users/{id}")
          .buildAndExpand(userSignedUpDto.id())
          .toUri();

        return ResponseEntity
          .created(location)
          .body(userSignedUpDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSignedUpDto> getUser(@PathVariable Long id) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.getUser(id));
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoggedUserDto> login(@RequestBody @Valid UserToLoginDto userToLoginDto) {

        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.login(userToLoginDto));
    }
}
