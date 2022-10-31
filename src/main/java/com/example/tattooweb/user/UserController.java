package com.example.tattooweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerNewUser(@RequestBody UserCreateDto userCreateDto){
        try {
            return userService.addNewUser(userCreateDto);
        }
        catch (EntityExistsException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) { userService.deleteUserById(userId);}

    @PutMapping(path = "/{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        userService.updateUser(userId, name, email);
    }
}
