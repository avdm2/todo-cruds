package org.mytodolist.domain.server;

import lombok.Data;
import lombok.experimental.Accessors;
import org.mytodolist.domain.entities.TaskPriority;
import org.mytodolist.domain.entities.TaskStatus;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskDto {

    String title;
    String description = "";

    LocalDateTime deadline = LocalDateTime.now().plusDays(14);

    TaskPriority taskPriority = TaskPriority.NO_PRIORITY;
    TaskStatus taskStatus = TaskStatus.NOT_SELECTED;
}
