package com.example.tattooweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getStudents(){
        return userService.getUsers();
    }

    @PostMapping
    public UserDto registerNewUser(@RequestBody UserCreateDto userCreateDto){
        return userService.addNewUser(userCreateDto);
    }
}
