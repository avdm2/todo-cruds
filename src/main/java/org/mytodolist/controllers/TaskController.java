package org.mytodolist.controllers;

import org.mytodolist.domain.entities.Task;
import org.mytodolist.domain.entities.TaskPriority;
import org.mytodolist.domain.entities.TaskStatus;
import org.mytodolist.domain.server.TaskDto;
import org.mytodolist.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        if (taskDto.getTitle() == null) {
            return new ResponseEntity<>(new Task(), HttpStatus.BAD_REQUEST);
        }
        // Time format: 2016-01-25T21:34:55 ({YYYY-MM-DD}T{HH:MM:SS})
        Task task = new Task()
                .setTitle(taskDto.getTitle())
                .setDescription(taskDto.getDescription())
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(taskDto.getDeadline())
                .setTaskPriority(taskDto.getTaskPriority())
                .setTaskStatus(taskDto.getTaskStatus());
        taskService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority) {
        return new ResponseEntity<>(taskService.getAllByPriority(priority), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        return new ResponseEntity<>(taskService.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable Integer id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        taskService.deleteTask(task.get());
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTaskPriority(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String title = taskDto.getTitle() == null ? task.get().getTitle() : taskDto.getTitle();
        String description = Objects.equals(taskDto.getDescription(), "") ? task.get().getDescription() : taskDto.getDescription();
        TaskPriority taskPriority = taskDto.getTaskPriority() == TaskPriority.NO_PRIORITY ? task.get().getTaskPriority() : taskDto.getTaskPriority();
        TaskStatus taskStatus = taskDto.getTaskStatus() == TaskStatus.NOT_SELECTED ? task.get().getTaskStatus() : taskDto.getTaskStatus();
        LocalDateTime deadline = taskDto.getDeadline() == LocalDateTime.MIN ? task.get().getDueTo() : taskDto.getDeadline();
        Task newTask = new Task()
                .setId(task.get().getId())
                .setTitle(title)
                .setDescription(description)
                .setCreatedAt(task.get().getCreatedAt())
                .setDueTo(deadline)
                .setTaskPriority(taskPriority)
                .setTaskStatus(taskStatus);
        taskService.saveTask(newTask);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }
}
