package org.mytodolist.model.converter;

import org.mapstruct.Mapper;
import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.dto.TaskDto;

@Mapper(componentModel = "spring")
public interface TaskConverter {
    
    TaskEntity convert(TaskDto dto);
}
