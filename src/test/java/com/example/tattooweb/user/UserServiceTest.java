package com.example.tattooweb.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

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
    void willThrowWhenEmailIsTaken() {
        // given
        UserCreateDto userCreateDto = new UserCreateDto(
                "Alex",
                "alex.jamal@gmail.com",
                LocalDate.of(2012, Month.APRIL, 13)
        );

        given(userRepository.selectExistisEmail(userCreateDto.getEmail()))
                .willReturn(true );
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
    void deletedUserSuccess() {
        // given
//        User user = new User(
//                1L,
//                "Alex",
//                "alex.jamal@gmail.com",
//                10,
//                LocalDate.of(2012, Month.APRIL, 13)
//        );
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

//        User capturedUser = userArgumentCaptor.getValue();
////        assertThat(capturedUser).isEqualTo(expectedUser); // ask? not same object?
//        assertThat(capturedUser);
//        when(userRepository.findUserById())
    }

    @Test
    @Disabled
    void updateUser() {
    }
}