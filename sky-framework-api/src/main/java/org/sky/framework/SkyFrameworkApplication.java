package org.sky.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.sky.framework.**")
@MapperScan("org.sky.framework.system.dao.**")
public class SkyFrameworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyFrameworkApplication.class, args);
    }
}