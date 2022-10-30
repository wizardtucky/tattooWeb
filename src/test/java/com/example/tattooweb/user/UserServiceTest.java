package com.example.tattooweb.user;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    User USER_1 = new User(1L, "Alex", "alex.jamal@gmail.com", 10, LocalDate.of(2012, Month.APRIL, 13));
//    User USER_2 = new User(2L, "Alex2", "alex2.jamal@gmail.com", 11, LocalDate.of(2011, Month.APRIL, 13));

    @Test
    void CanAddNewUser() {
        // given
        UserCreateDto userCreateDto = new UserCreateDto(
                "Alex",
                "alex.jamal@gmail.com",
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
    void shouldDeleteUser() {
//        assertThrows(IllegalStateException.class, () -> userService.deleteUser(123L));
//        assertThrows(IllegalStateException.class, () -> userService.deleteUser(5L));
        //given
        final User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );

        given(userRepository.findUserById(user.getId()))
                .willReturn(null);

        // when
        underTest.deleteUserById(user.getId());
        // then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .deleteById(userArgumentCaptor.capture().getId());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isEqualTo(user.getId());
    }

    @Test
    void userDeleteShouldThrowWhenUserIdIsNoneExistent() {
        //given
        final User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteUserById(user.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with id " + user.getId() + " doesn't exist");

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
    void testUpdateUser() {
        assertThrows(IllegalStateException.class, () -> userService.updateUser(1L, "Name", "jane.doe@example.org"));
        assertThrows(IllegalStateException.class, () -> userService.updateUser(3L, "Name", "jane.doe@example.org"));
        assertThrows(IllegalStateException.class, () -> userService.updateUser(2L, "Name", "jane.doe@example.org"));
        //assertThrows(IllegalStateException.class, () -> userService.updateUser(1L, "Name", ":UU "));
        assertThrows(IllegalStateException.class, () -> userService.updateUser(1L, "Name", ":UU "));
        System.out.println(underTest.getUsers());
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void shouldUpdateUser() {
        final User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );
        given(userRepository.save(user)).willReturn(user);

        // when
        underTest.updateUser(user.getId(), user.getName(), user.getEmail());

        // then


    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!
        //       at jdk.proxy5.$Proxy146.findById(null)
        //       at com.example.tattooweb.user.UserService.updateUser(UserService.java:36)
        //       at com.example.tattooweb.user.UserService$$FastClassBySpringCGLIB$$cdafc45f.invoke(<generated>)
        //   java.lang.IllegalArgumentException: The given id must not be null!
        //       at jdk.proxy5.$Proxy146.findById(null)
        //       at com.example.tattooweb.user.UserService.updateUser(UserService.java:36)
        //       at com.example.tattooweb.user.UserService$$FastClassBySpringCGLIB$$cdafc45f.invoke(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        userService.updateUser(null, "Name", "jane.doe@example.org");
    }

    @Test
    void updateUserShouldThrowIfUserIdDoesntExist() {
        // given
        User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );
        // when
        // then
        assertThatThrownBy(() -> underTest.updateUser(user.getId(), user.getName(), user.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("user with id " + user.getId() + " doesn't exist");
    }

    @Test
    void updateUserShouldThrowIfUserNameIsIncorrect() {
        // given
        User user = new User(
                1L,
                "",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );
        // when
        // then
        assertThatThrownBy(() -> underTest.updateUser(user.getId(), user.getName(), user.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has to be different and longer than 0 letter name");
    }

    @Test
    void updateUserShouldThrowIfUserEmailIsTakenOrIncorrect() {
        // given
        User user = new User(
                1L,
                "Alex",
                "",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );
        User userBuilder = User.builder()
                .id(2L)
                        .name("Joe")
                                .email("email@g.com")
                                        .age(10)
                                                .dateOfBirth(LocalDate.of(2012, Month.APRIL, 13))
                                                        .build();
        UserCreateDto sus = new UserCreateDto("Alex", "em", LocalDate.of(2012, Month.APRIL, 13));
        UserDto userDto = underTest.addNewUser(sus);
        // when
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        // then
//        assertThatThrownBy(() -> underTest.updateUser(user.getId(), user.getName(), user.getEmail()))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("email taken");
    }
    @Test
    void testUpdateUser5() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        userService.updateUser(1L, null, "jane.doe@example.org");
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser6() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        userService.updateUser(1L, "", "jane.doe@example.org");
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser7() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        userService.updateUser(1L, "Name", null);
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser8() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        userService.updateUser(1L, "Name", "");
    }


    @Test
    void creatingUserWillThrowWhenEmailIsTaken() {
        // given
        UserCreateDto userCreateDto = new UserCreateDto(
                "Alex",
                "alex.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)
        );

        given(userRepository.selectExistisEmail(userCreateDto.getEmail()))
                .willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> underTest.addNewUser(userCreateDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + userCreateDto.getEmail() + " taken");
        verify(userRepository, never()).save(any());
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    @Ignore// Noncompliant
    @Disabled
    void willThrowWhenUserIdDoesntExist() {
        // given
        User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );


        // when
//        underTest.deleteUser(user.getId());
//        // then
//        assertThatThrownBy(() -> underTest.deleteUser(user.getId()))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("User with id " + user.getId() + " doesn't exist");
//        verify(userRepository).deleteById(user.getId());

    }

    @Test
    void deletedUserSuccess() throws Exception {
        // given
        Long userId = 1L;
        User user = new User(
                1L,
                "Alex",
                "alex.jamal@gmail.com",
                10,
                LocalDate.of(2012, Month.APRIL, 13)
        );

//        underTest.addNewUser(userCreateDto);
//        System.out.println(underTest.getUsers());
//        underTest.deleteUser(userId);
//        verify(underTest, times(1)).deleteUser(userId);
//        willDoNothing().given(underTest).deleteUser(userId);

        // when
//        ResultActions response = mockMvc.perform(delete("/api/{userId}", userId));

        // git check
//        // when
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        underTest.deleteUser(user.getId());
//        // then
//
//        ArgumentCaptor<User> userArgumentCaptor =
//                ArgumentCaptor.forClass(User.class);
//        verify(userRepository)
//                .deleteById(userArgumentCaptor.capture().getId());
//        verify(userRepository).deleteById(user.getId());
//
//        User capturedUser = userArgumentCaptor.getValue();
////        assertThat(capturedUser).isEqualTo(expectedUser); // ask? not same object?
//        assertThat(capturedUser);
//        when(userRepository.findUserById())
    }

    @Test
    @Ignore //Noncompliant
    @Disabled
    void updateUser() {
    }
}