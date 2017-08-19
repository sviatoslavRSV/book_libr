package com.example.demo;

import com.example.demo.model.login.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.utils.ExtensionsAndPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;

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
        /*add image extensions*/
        ExtensionsAndPaths.setExtensionsImageSet(new HashSet<>(Arrays.asList("png", "jpg", "bmp")));
        /*add book extensions*/
        ExtensionsAndPaths.setExtensionsBookSet(new HashSet<>(Arrays.asList("txt", "rtf", "doc", "odt", "pdf")));


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
