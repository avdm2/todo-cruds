package org.mytodolist.services;

import org.mytodolist.domain.entities.Task;
import org.mytodolist.domain.entities.TaskPriority;
import org.mytodolist.domain.entities.TaskStatus;
import org.mytodolist.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllByStatus(TaskStatus status) {
        return taskRepository
                .findAll()
                .stream()
                .filter(task -> task.getTaskStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> getAllByPriority(TaskPriority priority) {
        return taskRepository
                .findAll()
                .stream()
                .filter(task -> task.getTaskPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> getAllSortedByDeadline() {
        List<Task> list = taskRepository.findAll();
        list.sort(Comparator
                .comparing(Task::getDueTo)
                .thenComparing(Task::getTaskPriority)
                .thenComparing(Task::getId));
        return list;
    }
}
