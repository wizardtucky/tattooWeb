package com.example.tattooweb.user;

import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private static UserRepository userRepository;
    private UserService underTest;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void userShoulsBeAdded() {
        // given
        UserCreateDto userCreateDto = new UserCreateDto(
                "Jhoshshhshsh",
                "Jhoshshhshsh.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)
        );
        // when
        underTest.addNewUser(userCreateDto);
        // then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        User expectedUser = UserMapper.toUser(userCreateDto);
//        assertThat(capturedUser).isEqualTo(expectedUser); // ask? not same object?
        assertThat(capturedUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedUser.getId())
                .hasFieldOrPropertyWithValue("name", expectedUser.getName())
                .hasFieldOrPropertyWithValue("email", expectedUser.getEmail())
                .hasFieldOrPropertyWithValue("age", expectedUser.getAge())
                .hasFieldOrPropertyWithValue("dateOfBirth", expectedUser.getDateOfBirth());
    }

    @Test
    void userCreateShouldThrowWhenUserEmailAlreadyExists() {
        UserCreateDto userCreateDto = new UserCreateDto(
                "Jhoshshhshsh",
                "Jhoshshhshsh.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)
        );
        userService.addNewUser(userCreateDto);
        assertThrows(EntityExistsException.class, () -> userService.addNewUser(userCreateDto));
    }
    @Test
    void should_delete_one_user() {
        userService.addNewUser(new UserCreateDto("Alexx",
                "alexxx.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)));
        long lastUserId = (long) userService.getUsers().size();
        userService.deleteUserById(lastUserId);
        long newLastUserId = (long) userService.getUsers().size();
        assertNotEquals(newLastUserId, lastUserId);
    }

    @Test
    void userDeleteShouldThrowWhenUserIdIsNoneExistent() {
        Long userId = new Random().longs(1, 10).findFirst().getAsLong();
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteUserById(userId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with id " + userId + " doesn't exist");

    }

    @Test
    public void user_exists_in_db_success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");
        user.setName("Nora");
        user.setDateOfBirth(LocalDate.of(2012, Month.APRIL, 13));
        user.setAge(10);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        // providing knowledge
        when(userRepository.findAll()).thenReturn(userList);

        List<User> fetchedUsers = userRepository.findAll();
        assertThat(fetchedUsers.size()).isPositive();
    }

    @Test
    void userUpdateShouldThrowWhenEmailIsIncorrect() {
        userService.addNewUser(new UserCreateDto("Alexxxx",
                "takenemail.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)));
        long lastUserId = (long) userService.getUsers().size();
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "AlexxP2", null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email is incorrect");
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "AlexxP3", ""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email is incorrect");
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "AlexxP4", "takenemail.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email is incorrect");
    }
    @Test
    void userUpdateShouldThrowWhenEmailAlreadyExists() {
        userService.addNewUser(new UserCreateDto("Alexx",
                "testJosh.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)));
        userService.addNewUser(new UserCreateDto("testJosh2",
                "testJosh2.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)));
        long lastUserId = (long) userService.getUsers().size();
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "testJosh22", "testJosh.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");
    }
    @Test
    void updateUserShouldThrowIfUserIdDoesntExist() {
        long lastUserId = (long) userService.getUsers().size() + 1L;
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "Alexx", "alexx.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("user with id " + lastUserId + " doesn't exist");
    }

    @Test
    void updateUserShouldThrowIfUserNameIsIncorrect() {
        userService.addNewUser(new UserCreateDto("Alexx",
                "alexx.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)));
        long lastUserId = (long) userService.getUsers().size();
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "", "alexx.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has to be different and longer than 0 letter name");
        assertThatThrownBy(() -> userService.updateUser(lastUserId, null, "alexx.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has to be different and longer than 0 letter name");
        assertThatThrownBy(() -> userService.updateUser(lastUserId, "Alexx", "alexx.jamal@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has to be different and longer than 0 letter name");
    }

//    @Test
//    void creatingUserWillThrowWhenEmailIsTaken() {
//        // given
//        UserCreateDto userCreateDto = new UserCreateDto(
//                "emailistaken",
//                "emailistaken.jamal@gmail.com",
//                LocalDate.of(2012, Month.APRIL, 13)
//        );
//        given(userRepository.selectExistisEmail(userCreateDto.getEmail()))
//                .willReturn(true);
//        // when
//        // then
//        assertThatThrownBy(() -> userService.addNewUser(userCreateDto))
//                .isInstanceOf(EntityExistsException.class)
//                .hasMessageContaining("Email " +userCreateDto.getEmail()+ " taken");
////        verify(userRepository, never()).deleteById(any());
//    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    @Ignore //Noncompliant
    @Disabled
    void updateUser() {
    }
}