package me.hiramchavez.todolist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.task.TaskBodyResDto;
import me.hiramchavez.todolist.dto.task.TaskRequestDto;
import me.hiramchavez.todolist.dto.task.TaskResponseDto;
import me.hiramchavez.todolist.mapper.TaskMapper;
import me.hiramchavez.todolist.model.ListTasks;
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

        String token = tokenService.getTokenFromHeader(request); //Get the token from the header
        String userEmail = tokenService.getVerifier(token).getSubject(); //Get the user email from the token
        User user = (User) userRepository.findByEmailAndActiveTrue(userEmail); //Get the user from the database

        if (taskRequestDto.tasks().size() < 1)
            throw new IllegalArgumentException("The list of tasks is empty. Add at least one task");

        //Get the list of tasks of the user and clean it
        ListTasks taskList = user
          .getListTasksById(taskRequestDto.list_task_id())
          .cleanTasks();

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
}
