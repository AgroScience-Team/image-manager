package com.github.agroscienceteam.imagemanager.configs;

import com.github.agroscienceteam.imagemanager.configs.annotations.Command;
import com.github.agroscienceteam.imagemanager.configs.annotations.DomainService;
import com.github.agroscienceteam.imagemanager.configs.annotations.Query;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "com.github.agroscienceteam.imagemanager",
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = DomainService.class),
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Command.class),
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Query.class),
    }
)
public class AnnotationConf {

}
