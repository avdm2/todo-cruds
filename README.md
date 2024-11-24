# MyTodoList - Java, Rust, CPP, Go, JS, Kotlin

## Фреймворки
- Java: `Spring`, `Project Reactor`
- Rust: todo
- C++: todo
- Go: todo
- JS: todo
- Kotlin: todo

## API

---

### Создание задачи [POST]
Route - **"/api/v1/tasks/create"**

Запрос:
```json
{
    "title": "Task 5",
    "description": "Simple taskEntity with no priority",
    "deadline": "2030-12-31T12:25:00",
    "taskStatus": "IN_PROGRESS",
    "taskPriority": "HIGH"
}
```

---

### Получение списка всех задач [GET]
Route - **"/api/v1/tasks"**

---

### Получение задачи по ID [GET]
Route - **"/api/v1/tasks/{id}"**

---

### Обновление задачи по ID [PUT]
Route - **"/api/v1/tasks/{id}"**

Запрос:
```json
{
    "title": "UpdatedTitle",
    "description": "UpdatedDescription",
    "deadline": "2025-01-01T00:00:00",
    "taskPriority": "MEDIUM",
    "taskStatus": "DONE"
}
```

---

### Получение списка задач по приоритетности [GET]
Route - **"api/v1/tasks/priority/{priority}"**

---

### Получение списка задач по статусу [GET]
Route - **"api/v1/tasks/status/{status}"**

---

### Удаление задачи по ID [DELETE]
Route - **"/api/v1/tasks/{id}"**
