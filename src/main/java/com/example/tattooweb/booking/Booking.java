package com.example.tattooweb.booking;

import com.example.tattooweb.tattoo.Tattoo;
import com.example.tattooweb.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bookingDate;
    @OneToMany
    @JoinColumn(name = "tattoo_id")
    private List<Tattoo> tattoo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Booking(Long id, LocalDate bookingDate, List<Tattoo> tattoo, User user) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.tattoo = tattoo;
        this.user = user;
    }
}
