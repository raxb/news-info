package com.newsinfo.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Bootstrap class for the application
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.newsinfo.*"})
@EntityScan("com.newsinfo*")
@EnableJpaRepositories("com.newsinfo.*")
public class InitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }

}
