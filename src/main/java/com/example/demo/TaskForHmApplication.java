package com.example.demo;

import com.example.demo.model.login.Role;
import com.example.demo.model.login.Userr;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.ExtensionsAndPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@SpringBootApplication
public class TaskForHmApplication {
    @Autowired
    private UserService userService;
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
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                Role role = roleRepository.findByRole("ADMIN");
                if (role == null) {
                    roleRepository.save(new Role("ADMIN"));
                }
                role = roleRepository.findByRole("USER");
                if (role == null) {
                    roleRepository.save(new Role("USER"));
                }

                Userr userAdmin = new Userr();
                userAdmin.setName("Admin");
                userAdmin.setLastName("Admin");
                userAdmin.setEmail("admin@gmail.com");
                userAdmin.setPassword("admin");
                userAdmin.setActive(true);
                userService.createAdminAcount(userAdmin);
                String token = UUID.randomUUID().toString();
                userService.createToken(userAdmin, token);
            }
        };
    }
}
