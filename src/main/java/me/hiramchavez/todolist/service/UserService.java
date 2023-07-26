package me.hiramchavez.todolist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.dto.user.*;
import me.hiramchavez.todolist.exception.user.UserAlreadyExistsException;
import me.hiramchavez.todolist.exception.user.UserNotFoundException;
import me.hiramchavez.todolist.mapper.UserMapper;
import me.hiramchavez.todolist.model.Role;
import me.hiramchavez.todolist.model.User;
import me.hiramchavez.todolist.repository.UserRepository;
import me.hiramchavez.todolist.security.PasswordUtils;
import me.hiramchavez.todolist.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserSignedUpDto signUp(UserToSignUpDto userToSignUpDto) {

        if (userRepository.existsByEmailAndActiveIsTrue(userToSignUpDto.email()))
            throw new UserAlreadyExistsException("User already exists in the database");

        // Obtener la contraseña en texto plano
        String plainPassword = userToSignUpDto.password();

        // Generar el hash de la contraseña
        String hashedPassword = PasswordUtils.generatePasswordHash(plainPassword);

        // Mapear los datos del DTO a la entidad
        User user = userMapper.toEntity(userToSignUpDto);

        // Asignar el hash de la contraseña a la entidad
        user.setPassword(hashedPassword);

        if (userToSignUpDto.role().equals(Role.ADMIN.name()))
            user.setRole(Role.USER);

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Mapear los datos de la entidad a un DTO y retornarlo
        return userMapper.userToUserSignedUpDto(user);
    }

    public LoggedUserDto login(UserToLoginDto userToLoginDto) {

        // Obtener el email del usuario
        String userEmail = userToLoginDto.email();

        if (!userRepository.existsByEmailAndActiveIsTrue(userEmail))
            throw new UserNotFoundException("User not found in the database");

        // Obtener el hash de la contraseña del usuario de la BD
        String hashedPassword = userRepository.findByEmailAndActiveTrue(userEmail).getPassword();

        // Verificar si la contraseña coincide con el hash
        boolean passwordMatches = PasswordUtils.verifyPassword(userToLoginDto.password(), hashedPassword);

        if (!passwordMatches)
            throw new RuntimeException("El usuario o contraseña no coincide");

        //Credenciales de autenticacion del usuario. Se usa el login como username y la clave como password
        Authentication authCredentials = new UsernamePasswordAuthenticationToken(
          userToLoginDto.email(),
          userToLoginDto.password()
        );

        // Autenticar al usuario
        User usuarioAutenticado = (User) authenticationManager.authenticate(authCredentials).getPrincipal();
        String token = tokenService.generateToken(usuarioAutenticado);

        return new LoggedUserDto(
          usuarioAutenticado.getId(),
          usuarioAutenticado.getFirstName(),
          usuarioAutenticado.getLastName(),
          usuarioAutenticado.getEmail(),
          token
        );
    }

    //Get user by token
    public UserSignedUpDto getUser(HttpServletRequest request) {

        User user = getUserFromDatabase(request);

        return userMapper.userToUserSignedUpDto(user);
    }

    //Allow an Admin get data of another user
    public UserSignedUpDto getUser(Long id, HttpServletRequest request) {
        User user = userRepository.getReferenceById(id);
        return userMapper.userToUserSignedUpDto(user);
    }

    public UserSignedUpDto updateUser(UserToUpdateDto userToUpdateDto, HttpServletRequest request) {
        User user = getUserFromDatabase(request);

        return userMapper.userToUserSignedUpDto(user.update(userToUpdateDto));
    }

    public ResponseDeleteDto deleteUser(HttpServletRequest request) {
        User user = getUserFromDatabase(request);
        user.setActive(false);
        return new ResponseDeleteDto(false, "User deleted successfully");
    }

    private User getUserFromDatabase(HttpServletRequest request) {
        String token = tokenService.getTokenFromHeader(request); //Get token from the header
        String userEmail = tokenService.getVerifier(token).getSubject(); //Get the user email from the token

        if (!userRepository.existsByEmailAndActiveIsTrue(userEmail))
            throw new UserNotFoundException("User not found in the database");

        return (User) userRepository.findByEmailAndActiveTrue(userEmail);
    }
}
