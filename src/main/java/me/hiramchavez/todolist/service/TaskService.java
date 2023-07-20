package me.hiramchavez.todolist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ResponseDeleteDto;
import me.hiramchavez.todolist.dto.task.*;
import me.hiramchavez.todolist.exception.task.ListOfTasksEmptyException;
import me.hiramchavez.todolist.exception.task.ListOfTasksNotFoundException;
import me.hiramchavez.todolist.exception.user.UserNotFoundException;
import me.hiramchavez.todolist.mapper.TaskMapper;
import me.hiramchavez.todolist.model.ListTasks;
import me.hiramchavez.todolist.model.State;
import me.hiramchavez.todolist.model.Task;
import me.hiramchavez.todolist.model.User;
import me.hiramchavez.todolist.repository.TaskRepository;
import me.hiramchavez.todolist.repository.UserRepository;
import me.hiramchavez.todolist.security.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TaskResponseDto createTasks(TaskRequestDto taskRequestDto, HttpServletRequest request) {

        //Get the user from the database using the token in the request
        //to validate the user
        User user = getUserFromDatabase(request);

        if (taskRequestDto.tasks().size() < 1)
            throw new ListOfTasksEmptyException("The list of tasks received to update is empty. Add at least one task");

        //Get the list of tasks of the user and clean it
        ListTasks taskList = user
          .getListTasksById(taskRequestDto.list_task_id())
          .cleanTasks();

        //Save the tasks in the database and add them to the list of tasks of the user
        List<TaskBodyResDto> listOfTaskBodyResDto = taskRequestDto.tasks().stream() //
          .map( taskBodyDto -> {
              Task task = taskRepository.save(taskMapper.toEntity(taskBodyDto)); //Save the task in the database
              taskList.addTask(task); //Add the task to the list of tasks of the user

              return taskMapper.taskToTaskBodyResDto(task);
          })
          .toList();

        //Return the task response dto
        return  new TaskResponseDto(
            user.getId(),
            taskList.getId(),
            listOfTaskBodyResDto
        );

    }

    public TaskResponseDto updateTasks(TaskRequestPutDto taskRequestPutDto, HttpServletRequest request) {

        User user = getUserFromDatabase(request);

        if (taskRequestPutDto.tasks().size() < 1)
            throw new ListOfTasksEmptyException("The list of tasks received to update is empty. Add at least one task");

        //Get user's list of tasks by ListTasks ID
        ListTasks listTasksFromUser = user.getListTasksById(taskRequestPutDto.list_task_id());

        if (listTasksFromUser == null)
            throw new ListOfTasksNotFoundException(String.format("The list of tasks with id: %d does not exist", taskRequestPutDto.list_task_id()));

        //Set the new list of tasks
        listTasksFromUser.getTasks().forEach( task -> {
            taskRequestPutDto.tasks().stream()
              .filter( taskBodyReqDto -> taskBodyReqDto.id().equals(task.getId()))
              .forEach(task::update);
        });

        return new TaskResponseDto(
          user.getId(),
          listTasksFromUser.getId(),
          listTasksFromUser.getTasks().stream()
            .map(taskMapper::taskToTaskBodyResDto)
            .toList()
        );

    }

    public ResponseDeleteDto deleteTasks(ListTaskReqDto listTaskReqDto, HttpServletRequest request) {

        User user = getUserFromDatabase(request);

        if (listTaskReqDto.listId().size() < 1)
            throw new ListOfTasksEmptyException("The list of tasks received is empty. Add at least one task");

        //Get the list of list tasks of the user
        List<ListTasks> listOfListTasksFromUser = user.getListTasks();

        if (listOfListTasksFromUser.isEmpty())
            throw new ListOfTasksEmptyException("The list of tasks from database is empty. Add at least one task in order to delete it");

        listOfListTasksFromUser
          .forEach(listTasks -> {
              listTasks.getTasks().stream()
                .filter(task -> listTaskReqDto.listId().contains(task.getId()))
                .forEach(task -> task.setState(State.DELETED));
          });

          return new ResponseDeleteDto(false, "Tasks deleted successfully");
    }

    private User getUserFromDatabase(HttpServletRequest request) {
        String token = tokenService.getTokenFromHeader(request); //Get token from the header
        String userEmail = tokenService.getVerifier(token).getSubject(); //Get the user email from the token

        if (!userRepository.existsByEmail(userEmail))
            throw new UserNotFoundException("User not found in the database");

        return (User) userRepository.findByEmailAndActiveTrue(userEmail);
    }
}
