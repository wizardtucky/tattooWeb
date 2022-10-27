package com.example.tattooweb.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository) {
        return  args -> {
            User alex = new User(
                    "Alex",
                    "alex.jamal@gmail.com",
                    LocalDate.of(2012, Month.APRIL, 13)
            );
            User oleg = new User(
                    "Oleg",
                    "oleg.jamal@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 13)
            );

            repository.saveAll(
                    List.of(alex, oleg)
            );
        };
    }
}

