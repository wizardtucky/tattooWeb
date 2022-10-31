package com.example.tattooweb.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String name;
    private String email;
//    @Transient
    private LocalDate dateOfBirth;

}
