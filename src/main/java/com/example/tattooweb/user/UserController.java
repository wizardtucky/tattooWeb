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

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userID") Long userId) { userService.deleteUser(userId);}

    @PutMapping(path = "{userId}")
    public void updateStudent(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        userService.updateUser(userId, name, email);
    }

}
