package me.hiramchavez.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hiramchavez.todolist.dto.user.UserSignedUpDto;
import me.hiramchavez.todolist.dto.user.UserToSignUpDto;
import me.hiramchavez.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserToSignUpDto userToSignUpDto;
    private UserSignedUpDto userSignedUpDto;

    @BeforeEach
    void setUp() {
        userToSignUpDto = new UserToSignUpDto(
          "John",
          "Doe",
          "USER",
          "john.doe@gmail.com",
          "123456",
          true
        );

        userSignedUpDto = new UserSignedUpDto(
          1L,
          "John",
          "Doe",
          "john.doe@gmail.com",
          true,
          "USER",
          null
        );
    }

    @Test
    void testSignUp() {
        //Cuando el mock del servicio invoque el metodo "signUp" va a regresar el DTO del usuario registrado
        when(userService.signUp(userToSignUpDto)).thenReturn(userSignedUpDto);

        UriComponentsBuilder URI = UriComponentsBuilder.fromUri(UriComponentsBuilder
          .fromUriString("/users/{id}")
          .buildAndExpand(userSignedUpDto.id())
          .toUri());

        assertEquals(userSignedUpDto, userController.signUp(userToSignUpDto, URI).getBody());
    }
}
