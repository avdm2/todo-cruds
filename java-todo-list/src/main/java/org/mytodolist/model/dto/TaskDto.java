package org.mytodolist.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class TaskDto {

    private String title;
    private String description;

    private LocalDateTime dueTo;

    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
}
