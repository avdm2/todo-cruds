package org.mytodolist.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mytodolist.configuration.IntegrationTestConfiguration;
import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ExtendWith(SpringExtension.class)
@Import(TaskRepositoryTest.class)
@SqlGroup({
        @Sql(scripts = "/sql/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(scripts = "/sql/insert_todo_tasks.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TaskRepositoryTest extends IntegrationTestConfiguration {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Получение списка задач по приоритету")
    void findByTaskPriority_Success() {
        Flux<TaskEntity> taskEntities = taskRepository.findByTaskPriority(TaskPriority.HIGH);
        StepVerifier.create(taskEntities)
                .expectNextCount(2)
                .expectNext(new TaskEntity().setId(4).setTitle("Task 4").setDescription("Description 4").setTaskPriority(TaskPriority.HIGH))
                .expectNext(new TaskEntity().setId(8).setTitle("Task 8").setDescription("Description 8").setTaskPriority(TaskPriority.HIGH))
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение списка задач по статусу")
    void findByTaskStatus_Success() {
        Flux<TaskEntity> taskEntities = taskRepository.findByTaskStatus(TaskStatus.IN_PROGRESS);
        StepVerifier.create(taskEntities)
                .expectNextCount(2)
                .expectNext(new TaskEntity().setId(2).setTitle("Task 2").setDescription("Description 2").setTaskStatus(TaskStatus.IN_PROGRESS))
                .expectNext(new TaskEntity().setId(6).setTitle("Task 5").setDescription("Description 5").setTaskStatus(TaskStatus.IN_PROGRESS))
                .verifyComplete();
    }
}
