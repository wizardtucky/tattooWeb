package com.example.tattooweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserDto addNewUser(UserCreateDto userCreateDto) {
        User newUser = UserMapper.toUser(userCreateDto);
        if (userRepository.selectExistisEmail(userCreateDto.getEmail())) {
            throw new IllegalStateException("Email " + userCreateDto.getEmail() + " taken");
        }
        userRepository.save(newUser);
        return UserMapper.toUserDto(newUser);
    }

    public List<User> getUsers() { return userRepository.findAll(); }

    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new IllegalStateException("User with id " + userId + " doesn't exist");
        }
        userRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "user with id " + userId + " doesn't exist"
                ));
        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(user.getEmail(), email)) {
            if (userRepository
                    .findUserByEmail(email)
                    .isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }

    }
}
