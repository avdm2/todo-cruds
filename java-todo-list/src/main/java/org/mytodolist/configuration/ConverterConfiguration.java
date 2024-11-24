package org.mytodolist.configuration;

import lombok.AllArgsConstructor;
import org.mytodolist.util.TaskPriorityConverter;
import org.mytodolist.util.TaskStatusConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@AllArgsConstructor
public class ConverterConfiguration implements WebFluxConfigurer {

    private final TaskStatusConverter taskStatusConverter;
    private final TaskPriorityConverter taskPriorityConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(taskStatusConverter);
        registry.addConverter(taskPriorityConverter);
    }
}
