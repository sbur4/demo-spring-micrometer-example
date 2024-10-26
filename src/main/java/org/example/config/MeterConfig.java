package org.example.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class MeterConfig {
    @Bean
    MeterFilter commonTagsMeterFilter() {
        return MeterFilter.commonTags(Collections.singletonList(Tag.of("application", "my-spring-boot-app")));
    }
}
