package com.example.tattooweb.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
//    private LocalDate dateOfBirth;
}
