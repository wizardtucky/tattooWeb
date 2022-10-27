package com.example.tattooweb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findUserByUserName(String userName);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long userId);
    @Query("" +
            "SELECT CASE WHEN COUNT(u) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM User u " +
            "WHERE u.email =?1"
    )
    Boolean selectExistisEmail(String email);
}
