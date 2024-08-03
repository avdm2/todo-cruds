package org.mytodolist.util;

import org.mytodolist.model.enums.TaskStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusConverter implements Converter<String, TaskStatus> {

    @Override
    public TaskStatus convert(String source) {
        return TaskStatus.valueOf(source.toUpperCase());
    }
}
