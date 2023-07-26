package me.hiramchavez.todolist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.dto.listTasks.ListTasksReqDto;
import me.hiramchavez.todolist.dto.listTasks.ListTasksResDto;
import me.hiramchavez.todolist.exception.listtasks.ListTasksNotFoundException;
import me.hiramchavez.todolist.exception.user.UserNotFoundException;
import me.hiramchavez.todolist.mapper.ListTasksMapper;
import me.hiramchavez.todolist.model.ListTasks;
import me.hiramchavez.todolist.model.User;
import me.hiramchavez.todolist.repository.ListTasksRepository;
import me.hiramchavez.todolist.repository.UserRepository;
import me.hiramchavez.todolist.security.TokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTasksService {

    private final ListTasksRepository listTasksRepository;
    private final ListTasksMapper listTasksMapper;
    private final TokenService tokenService;

    private final UserRepository userRepository;

    public ListTasksResDto createListTask(ListTasksReqDto listTasksReqDto, HttpServletRequest request) {

        //Get the user from the database using the token in the request
        //to validate the user
        User user = getUserFromDatabase(request);

        ListTasks listTasks = listTasksMapper.toEntity(listTasksReqDto); //Convert the list tasks request dto to an entity

        user.addListTasks(listTasks); //Add the list tasks to the user

        return
          listTasksMapper.toDto( //Save the list tasks in the database and Return the list tasks response dto
            listTasksRepository.save(listTasks)
          );
    }

    public ListTasksResDto updateListTask(
      Long id,
      ListTasksReqDto listTasksReqDto,
      HttpServletRequest request
    ) {
        //Get the user from the database using the token in the request
        //to validate the user
        User user = getUserFromDatabase(request);

        ListTasks listTasks = user.getListTasksById(id); //Get the list tasks from the user by id

        if (listTasks == null) //If the list tasks is null
            throw new ListTasksNotFoundException("List tasks not found in the database"); //Throw an exception

        listTasks.update(listTasksReqDto); //Update data

        return listTasksMapper.toDto(listTasks); //Return the list tasks response dto
    }

    public ResponseDeleteDto deleteListTask(Long id, HttpServletRequest request) {

        User user = getUserFromDatabase(request);

        ListTasks listTasks = user.getListTasksById(id); //Get the list tasks from the user by id

        if (listTasks == null) //If the list tasks is null
            throw new ListTasksNotFoundException("List tasks not found in the database"); //Throw an exception

        listTasks.delete(); //set active to false

        return new ResponseDeleteDto(false, "List tasks deleted successfully"); //Return the response delete dto
    }

    private User getUserFromDatabase(HttpServletRequest request) {
        String token = tokenService.getTokenFromHeader(request); //Get token from the header
        String userEmail = tokenService.getVerifier(token).getSubject(); //Get the user email from the token

        if (!userRepository.existsByEmailAndActiveIsTrue(userEmail))
            throw new UserNotFoundException("User not found in the database");

        return (User) userRepository.findByEmailAndActiveTrue(userEmail);
    }
}
