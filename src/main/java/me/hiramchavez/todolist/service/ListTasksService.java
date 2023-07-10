package me.hiramchavez.todolist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.dto.ListTasksReqDto;
import me.hiramchavez.todolist.dto.ListTasksResDto;
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

        String token = tokenService.getTokenFromHeader(request);
        String userEmail = tokenService.getVerifier(token).getSubject();
        User user = (User) userRepository.findByEmailAndActiveTrue(userEmail);

        ListTasks listTasks = listTasksMapper.toEntity(listTasksReqDto);

        user.addListTasks(listTasks);

        return
          listTasksMapper.toDto(
            listTasksRepository.save(listTasks)
          );
    }
}
