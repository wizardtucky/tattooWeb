package com.example.tattooweb.user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository underTest;
//
//    @Test
//    @Disabled
//    void itShouldCheckIfStudentExistsEmail() {
//        String email = "Jamal@gmail.com";
//        User user = new User(
//                "Jamal",
//                email,
//                LocalDate.of(2012, Month.APRIL, 13)
//        );
//        underTest.save(user);
//        //when
//        Optional<User> expected = underTest.findUserByEmail(email);
//        //then
//        assertThat(expected).isPresent();
//    }
//}