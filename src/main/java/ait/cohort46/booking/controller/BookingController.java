package ait.cohort46.booking.controller;

import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;
import ait.cohort46.booking.dto.ResponseStatusBookingDto;
import ait.cohort46.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseBookingDto addBooking(@RequestBody CreateBookingDto createBookingDto) {
        return bookingService.addBooking(createBookingDto);
    }

    @PatchMapping("/bookings/{id}")
    public ResponseStatusBookingDto changeStatusBooking(@PathVariable Long id, @RequestBody NewStatusBooking newStatusBooking) {
        return bookingService.changeStatusBooking(id, newStatusBooking);
    }

    @GetMapping("/bookings/{id}")
    public ResponseBookingDto getBooking(@PathVariable Long id) {

        return bookingService.getBooking(id);
    }


}
