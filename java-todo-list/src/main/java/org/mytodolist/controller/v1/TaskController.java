package org.mytodolist.controller.v1;

import org.mytodolist.entity.TaskEntity;
import org.mytodolist.model.dto.TaskDto;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.mytodolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<TaskEntity>> createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<List<TaskEntity>>> getAllTasks() {
        return taskService.getAllTasks().collectList()
                .map(tasks -> tasks.isEmpty()
                        ? ResponseEntity.notFound().build()
                        : ResponseEntity.ok(tasks));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskEntity>> getTaskById(@PathVariable Integer id) {
        return taskService.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskEntity>> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(id, taskDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/priority/{priority}")
    public Mono<ResponseEntity<List<TaskEntity>>> getAllByPriority(@PathVariable TaskPriority priority) {
        return taskService.getAllByPriority(priority).collectList()
                .map(tasks -> tasks.isEmpty()
                        ? ResponseEntity.notFound().build()
                        : ResponseEntity.ok(tasks));
    }

    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<List<TaskEntity>>> getAllByStatus(@PathVariable TaskStatus status) {
        return taskService.getAllByStatus(status).collectList()
                .map(tasks -> tasks.isEmpty()
                        ? ResponseEntity.notFound().build()
                        : ResponseEntity.ok(tasks));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTaskById(@PathVariable Integer id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
