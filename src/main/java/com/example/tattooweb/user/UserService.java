package com.example.tattooweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserDto addNewUser(UserCreateDto userCreateDto) {
        User newUser = UserMapper.toUser(userCreateDto);
        userRepository.save(newUser);
        return UserMapper.toUserDto(newUser);
    }

    public List<User> getUsers() { return userRepository.findAll(); }

}
