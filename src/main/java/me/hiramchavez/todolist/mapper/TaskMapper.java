package me.hiramchavez.todolist.mapper;

import me.hiramchavez.todolist.dto.task.TaskBodyReqDto;
import me.hiramchavez.todolist.dto.task.TaskBodyResDto;
import me.hiramchavez.todolist.dto.task.TaskResponseDto;
import me.hiramchavez.todolist.model.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    Task toEntity(TaskResponseDto taskResponseDto);

    TaskBodyResDto toDto(Task task);

    Task toEntity(TaskBodyReqDto taskBodyDto);

    TaskBodyReqDto taskToTaskBodyDto(Task task);

    TaskBodyResDto taskToTaskBodyResDto(Task task);

    Task toEntity(TaskBodyResDto taskBodyResDto);

}