package org.mytodolist.repository;

import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends R2dbcRepository<TaskEntity, Integer> {

    Flux<TaskEntity> findByTaskPriority(TaskPriority taskPriority);

    Flux<TaskEntity> findByTaskStatus(TaskStatus taskStatus);
}
