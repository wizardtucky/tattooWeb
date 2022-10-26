package com.example.tattooweb.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String name;
    private String email;
//    @Transient
//    private Integer age;
    private LocalDate dateOfBirth;

}
