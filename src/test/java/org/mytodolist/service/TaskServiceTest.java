package org.mytodolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.converter.TaskConverter;
import org.mytodolist.model.dto.TaskDto;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.mytodolist.repository.TaskRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskConverter taskConverter;

    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository, taskConverter);
    }

    @Test
    @DisplayName("Успешное создание задачи")
    public void createTask_Success() {
        TaskDto taskDto = new TaskDto()
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE);

        TaskEntity taskEntity = new TaskEntity()
                .setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskConverter.convert(taskDto)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(Mono.just(taskEntity));

        Mono<TaskEntity> result = taskService.createTask(taskDto);

        StepVerifier.create(result)
                .expectNextMatches(savedTask -> savedTask.getId() != null)
                .verifyComplete();

        verify(taskConverter, times(1)).convert(taskDto);
        verify(taskRepository, times(1)).save(taskEntity);
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

        when(taskRepository.findAll()).thenReturn(Flux.just(taskEntity));

        Flux<TaskEntity> result = taskService.getAllTasks();

        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Успешное получение задачи по ID")
    public void findById_Success() {
        TaskEntity taskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskRepository.findById(1)).thenReturn(Mono.just(taskEntity));

        Mono<TaskEntity> result = taskService.findById(1);

        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();

        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Успешное обновление задачи")
    public void updateTask_Success() {
        TaskDto taskDto = new TaskDto().setTitle("Updated Task")
                .setDescription("Updated Description")
                .setTaskPriority(TaskPriority.LOW)
                .setTaskStatus(TaskStatus.DONE);

        TaskEntity existingTaskEntity = new TaskEntity().setId(1)
                .setTitle("Test Task")
                .setDescription("Description")
                .setTaskPriority(TaskPriority.MEDIUM)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        TaskEntity updatedTaskEntity = new TaskEntity().setId(1)
                .setTitle("Updated Task")
                .setDescription("Updated Description")
                .setTaskPriority(TaskPriority.LOW)
                .setTaskStatus(TaskStatus.DONE)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(LocalDateTime.now().plusDays(7));

        when(taskRepository.findById(1)).thenReturn(Mono.just(existingTaskEntity));
        when(taskRepository.save(updatedTaskEntity)).thenReturn(Mono.just(updatedTaskEntity));

        Mono<TaskEntity> result = taskService.updateTask(1, taskDto);

        StepVerifier.create(result)
                .expectNextMatches(savedTask -> savedTask.getTitle().equals("Updated Task"))
                .verifyComplete();

        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(updatedTaskEntity);
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

        when(taskRepository.findByTaskPriority(TaskPriority.MEDIUM)).thenReturn(Flux.just(taskEntity));

        Flux<TaskEntity> result = taskService.getAllByPriority(TaskPriority.MEDIUM);

        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();

        verify(taskRepository, times(1)).findByTaskPriority(TaskPriority.MEDIUM);
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

        when(taskRepository.findByTaskStatus(TaskStatus.DONE)).thenReturn(Flux.just(taskEntity));

        Flux<TaskEntity> result = taskService.getAllByStatus(TaskStatus.DONE);

        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();

        verify(taskRepository, times(1)).findByTaskStatus(TaskStatus.DONE);
    }

    @Test
    @DisplayName("Успешное удаление задачи по ID")
    public void deleteById_Success() {
        when(taskRepository.deleteById(1)).thenReturn(Mono.empty());

        Mono<Void> result = taskService.deleteById(1);

        StepVerifier.create(result)
                .verifyComplete();

        verify(taskRepository, times(1)).deleteById(1);
    }
}