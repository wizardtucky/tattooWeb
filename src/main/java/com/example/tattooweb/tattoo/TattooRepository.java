package com.example.tattooweb.tattoo;

import com.example.tattooweb.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TattooRepository extends JpaRepository<Tattoo,Long> {
}
