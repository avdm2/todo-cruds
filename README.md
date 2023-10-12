# MyTodoList

## Стек
- Язык: `Java`
- Фреймворк: `Spring Boot`
- СУБД: `PostgreSQL`
- API-requests: `Postman`
## API

---

### Получение задачи по ID [GET]
Route - **"/api/tasks/{id}"**

---

### Удаление задачи по ID [DELETE]
Route - **"/api/tasks/{id}"**

---

### Обновление задачи по ID [PUT]
Route - **"/api/tasks/{id}"**

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

### Получение списка всех задач [GET]
Route - **"/api/tasks"**

---

### Создание задачи [POST]
Route - **"/api/tasks/create"**

Запрос:
```json
{
    "title": "Task 5",
    "description": "Simple task with no priority",
    "deadline": "2030-12-31T12:25:00",
    "taskStatus": "IN_PROGRESS",
    "taskPriority": "HIGH"
}
```

---

### Получение списка задач по приоритетности [GET]
Route - **"api/tasks/priority/{priority}"**

---

### Получение списка задач по статусу [GET]
Route - **"api/tasks/status/{status}"**
