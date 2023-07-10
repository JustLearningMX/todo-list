package me.hiramchavez.todolist.mapper;

import me.hiramchavez.todolist.dto.ListTasksReqDto;
import me.hiramchavez.todolist.dto.ListTasksResDto;
import me.hiramchavez.todolist.model.ListTasks;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTasksMapper {
    ListTasks toEntity(ListTasksResDto listTasksResDto);

    ListTasksResDto toDto(ListTasks listTasks);

    ListTasks toEntity(ListTasksReqDto listTasksReqDto);

    ListTasksReqDto listTasksToListTasksReqDto(ListTasks listTasks);

}