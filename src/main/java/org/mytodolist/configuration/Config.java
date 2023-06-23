package org.mytodolist.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("org.mytodolist.domain.entities")
public class Config {
}
