package com.example.tattooweb.user;

import javax.persistence.Transient;
import java.time.LocalDate;

public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto){
        return User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .dateOfBirth(userCreateDto.getDateOfBirth())
                .build();
    }
    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

}

//    private Long id;
//    private String name;
//    private String email;
//    @Transient
//    private Integer age;
//    private LocalDate dateOfBirth;
