package org.mytodolist.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.dto.TaskDto;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.mytodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class TaskControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Mock
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Успешное создание задачи")
    public void createTask_Success() {
        TaskDto taskDto = new TaskDto().setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setDueTo(LocalDateTime.now().plusDays(7));

        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskService.createTask(any(TaskDto.class))).thenReturn(Mono.just(taskEntity));

        webClient.post()
                .uri("/api/v1/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskEntity.class)
                .isEqualTo(taskEntity);
    }

    @Test
    @DisplayName("Успешное получение всех задач")
    public void getAllTasks_Success() {
        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        List<TaskEntity> tasks = Collections.singletonList(taskEntity);

        when(taskService.getAllTasks()).thenReturn(Flux.fromIterable(tasks));

        webClient.get()
                .uri("/api/v1/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskEntity.class)
                .isEqualTo(tasks);
    }

    @Test
    @DisplayName("Успешное получение задачи по ID")
    public void getTaskById_Success() {
        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskService.findById(1)).thenReturn(Mono.just(taskEntity));

        webClient.get()
                .uri("/api/v1/tasks/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskEntity.class)
                .isEqualTo(taskEntity);
    }

    @Test
    @DisplayName("Успешное обновление задачи")
    public void updateTask_Success() {
        TaskDto taskDto = new TaskDto().setTitle("Updated Task")
                .setDescription("Updated Description")
                .setTaskPriority(TaskPriority.LOW)
                .setTaskStatus(TaskStatus.DONE)
                .setDueTo(LocalDateTime.now().plusDays(7));

        TaskEntity updatedTaskEntity = new TaskEntity().setId(1)
                .setTitle("Updated Task")
                .setDescription("Updated Description")
                .setTaskPriority(TaskPriority.LOW)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskService.updateTask(1, taskDto)).thenReturn(Mono.just(updatedTaskEntity));

        webClient.put()
                .uri("/api/v1/tasks/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskEntity.class)
                .isEqualTo(updatedTaskEntity);
    }

    @Test
    @DisplayName("Успешное получение задач по приоритету")
    public void getAllByPriority_Success() {
        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        List<TaskEntity> tasks = Collections.singletonList(taskEntity);

        when(taskService.getAllByPriority(TaskPriority.MEDIUM)).thenReturn(Flux.fromIterable(tasks));

        webClient.get()
                .uri("/api/v1/tasks/priority/{priority}", TaskPriority.MEDIUM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskEntity.class)
                .isEqualTo(tasks);
    }

    @Test
    @DisplayName("Успешное получение задач по статусу")
    public void getAllByStatus_Success() {
        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        List<TaskEntity> tasks = Collections.singletonList(taskEntity);

        when(taskService.getAllByStatus(TaskStatus.DONE)).thenReturn(Flux.fromIterable(tasks));

        webClient.get()
                .uri("/api/v1/tasks/status/{status}", TaskStatus.DONE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskEntity.class)
                .isEqualTo(tasks);
    }

    @Test
    @DisplayName("Успешное удаление задачи по ID")
    public void deleteTaskById_Success() {
        when(taskService.deleteById(1)).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/api/v1/tasks/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }
}
