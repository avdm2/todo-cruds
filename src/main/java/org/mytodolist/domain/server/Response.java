package org.mytodolist.domain.server;

import lombok.Data;
import lombok.experimental.Accessors;
import org.mytodolist.domain.entities.Task;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Response {

    String string;
    Task task = new Task();
    List<Task> list = new ArrayList<>();
}
