package org.mytodolist.controllers;

import org.mytodolist.domain.entities.Task;
import org.mytodolist.domain.server.Response;
import org.mytodolist.domain.server.TaskDto;
import org.mytodolist.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createTask(@RequestBody TaskDto taskDto) {
        Response response = new Response();
        if (taskDto.getTitle() == null) {
            return new ResponseEntity<>(response.setString("Ошибка! Название задачи не может быть пустым!"), HttpStatus.BAD_REQUEST);
        }
        Task task = new Task()
                .setTitle(taskDto.getTitle())
                .setDescription(taskDto.getDescription())
                .setCreatedAt(LocalDateTime.now())
                .setDueTo(taskDto.getDeadline())
                .setTaskPriority(taskDto.getTaskPriority())
                .setTaskStatus(taskDto.getTaskStatus());
        taskService.saveTask(task);
        return new ResponseEntity<>(response.setString("Задача успешно добавлена!").setTask(task), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response> getAllTasks() {
        Response response = new Response();
        List<Task> list = taskService.getAllTasks();
        if (list.isEmpty()) {
            return new ResponseEntity<>(response.setString("Задач нет!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.setString("Список содержит в себе " + list.size() + " задач!").setList(list), HttpStatus.OK);
    }
}
