package com.example.tattooweb.booking;

import com.example.tattooweb.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api3/")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    @GetMapping
    public List<Booking> getBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping
    private void registerNewBooking(@RequestBody Booking booking){
        bookingService.addNewBooking(booking);
    }
}
