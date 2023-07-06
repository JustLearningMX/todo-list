package me.hiramchavez.todolist.mapper;

import me.hiramchavez.todolist.dto.LoggedUserDto;
import me.hiramchavez.todolist.dto.UserSignedUpDto;
import me.hiramchavez.todolist.dto.UserToLoginDto;
import me.hiramchavez.todolist.dto.UserToSignUpDto;
import me.hiramchavez.todolist.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
//@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserToSignUpDto userToSignUpDto);

    UserToSignUpDto toDto(User user);

    User toEntity(UserSignedUpDto userSignedUpDto);

    UserSignedUpDto userToUserSignedUpDto(User user);

    User toEntity(UserToLoginDto userToLoginDto);

    UserToLoginDto userToUserToLoginDto(User user);

    User toEntity(LoggedUserDto loggedUserDto);

    LoggedUserDto userToLoggedUserDto(User user);

}