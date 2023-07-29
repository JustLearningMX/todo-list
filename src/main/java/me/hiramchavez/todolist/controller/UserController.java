package me.hiramchavez.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.dto.user.*;
import me.hiramchavez.todolist.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Users", description = "Manage all endpoints about Users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
      summary = "Sign Up a new User.",
      description = "Let a user sign up with the email account."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User created successfully",
        content = {
          @Content(mediaType = "application/json",
          schema = @Schema(implementation = UserSignedUpDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "User Already Exists", content = {@Content})
    })
    @SecurityRequirements()
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

    @Operation(
      summary = "User Login section.",
      description = "Let a user login with the email account. Return a token"
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User logged successfully",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = LoggedUserDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "User data login incorrect", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @SecurityRequirements()
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoggedUserDto> login(@RequestBody @Valid UserToLoginDto userToLoginDto) {

        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.login(userToLoginDto));
    }

    @Operation(
      summary = "Get a user using its token.",
      description = "Let a user get its data using the token."
      //tags = { "users", "get" }
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserSignedUpDto.class))
        }),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<UserSignedUpDto> getUser(HttpServletRequest request) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.getUser(request));
    }

    @Operation(
      summary = "Admin get a user by ID.",
      description = "Let an Admin get any user data using the User's ID. Token is required."
      //tags = { "users", "get" }
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserSignedUpDto.class))
        }),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserSignedUpDto> getUser(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.getUser(id, request));
    }

    @Operation(
      summary = "Update data user using token.",
      description = "Let a User update data using the Token."
      //tags = { "users", "put" }
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User updated successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserSignedUpDto.class))
        }),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content})
    })
    @PutMapping
    @Transactional
    public ResponseEntity<UserSignedUpDto> updateUser(
      @RequestBody @Valid UserToUpdateDto userToUpdateDto, HttpServletRequest request) {

        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.updateUser(userToUpdateDto, request));

    }

    @Operation(
      summary = "Delete data user using token.",
      description = "Let a User delete its data using the Token."
      //tags = { "users", "delete" }
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "User deleted successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDto.class))
        }),
      @ApiResponse(responseCode = "404", description = "User Not Found", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping
    @Transactional
    public ResponseEntity<ResponseDeleteDto> deleteUser(HttpServletRequest request) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(userService.deleteUser(request));
    }
}
