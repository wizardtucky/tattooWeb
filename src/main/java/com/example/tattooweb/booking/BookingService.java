package com.example.tattooweb.booking;

import com.example.tattooweb.tattoo.TattooRepository;
import com.example.tattooweb.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TattooRepository tattooRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void addNewBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
