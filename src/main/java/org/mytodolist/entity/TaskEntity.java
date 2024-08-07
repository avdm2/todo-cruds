package org.mytodolist.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.mytodolist.model.enums.TaskPriority;
import org.mytodolist.model.enums.TaskStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Table("todo.tasks")
@EqualsAndHashCode
public class TaskEntity {

    @Id
    private Integer id;

    @NonNull
    private String title;

    private String description;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("due_to")
    private LocalDateTime dueTo;

    @Column("priority")
    private TaskPriority taskPriority;

    @Column("status")
    private TaskStatus taskStatus;
}
