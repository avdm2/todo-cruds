package org.mytodolist.util;

import org.mytodolist.model.enums.TaskPriority;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskPriorityConverter implements Converter<String, TaskPriority> {

    @Override
    public TaskPriority convert(String source) {
        return TaskPriority.valueOf(source.toUpperCase());
    }
}
