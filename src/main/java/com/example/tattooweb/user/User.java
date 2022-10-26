package com.example.tattooweb.user;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String email;
    @Transient
    private Integer age;
    private LocalDate dateOfBirth;

    public User(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
