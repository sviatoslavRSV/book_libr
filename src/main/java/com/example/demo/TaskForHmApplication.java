package com.example.demo;

import com.example.demo.model.login.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskForHmApplication {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(TaskForHmApplication.class, args);
//		SpringApplication.run(TaskForHmApplication.class, "--debug");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return strings -> {
            Role role;
            role = roleRepository.findByRole("ADMIN");
            if (role == null) {
                roleRepository.save(new Role("ADMIN"));
            }
            role = roleRepository.findByRole("USER");
            if (role == null) {
                roleRepository.save(new Role("USER"));
            }
        };
    }
}
