package org.mytodolist.service;

import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.converter.TaskConverter;
import org.mytodolist.model.dto.TaskDto;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.mytodolist.repository.TaskRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;

    public TaskService(TaskRepository taskRepository, TaskConverter taskConverter) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
    }

    public Mono<TaskEntity> createTask(TaskDto taskDto) {
        return taskRepository.save(taskConverter.convert(taskDto)
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(Optional.ofNullable(taskDto.getDueTo())
                        .orElse(LocalDateTime.now().plusDays(7))));
    }

    public Flux<TaskEntity> getAllTasks() {
        return taskRepository.findAll()
                .collectList()
                .map(list -> list.stream()
                        .sorted(Comparator
                                .comparing(TaskEntity::getDueTo)
                                .thenComparing(TaskEntity::getTaskPriority)
                                .thenComparing(TaskEntity::getId))
                        .toList())
                .onErrorComplete()
                .flatMapIterable(list -> list);
    }

    public Mono<TaskEntity> findById(Integer id) {
        return taskRepository.findById(id);
    }

    public Mono<TaskEntity> updateTask(Integer id, TaskDto taskDto) {
        return taskRepository.findById(id)
                .map(taskEntity -> {
                    Optional.of(taskDto.getTitle()).ifPresent(taskEntity::setTitle);
                    Optional.of(taskDto.getDescription()).ifPresent(taskEntity::setDescription);
                    Optional.of(taskDto.getDueTo()).ifPresent(taskEntity::setDueTo);
                    Optional.of(taskDto.getTaskPriority()).ifPresent(taskEntity::setTaskPriority);
                    Optional.of(taskDto.getTaskStatus()).ifPresent(taskEntity::setTaskStatus);
                    return taskEntity;
                })
                .onErrorComplete()
                .flatMap(taskRepository::save);
    }

    public Flux<TaskEntity> getAllByPriority(TaskPriority taskPriority) {
        return taskRepository.findByTaskPriority(taskPriority);
    }

    public Flux<TaskEntity> getAllByStatus(TaskStatus taskStatus) {
        return taskRepository.findByTaskStatus(taskStatus);
    }

    public Mono<Void> deleteById(Integer id) {
        return taskRepository.deleteById(id);
    }
}
