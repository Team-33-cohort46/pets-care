package ait.cohort46.booking.controller;

import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;
import ait.cohort46.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/api/bookings")
    public ResponseBookingDto addBooking(@RequestBody CreateBookingDto createBookingDto) {
        return bookingService.addBooking(createBookingDto);
    }

    @PatchMapping("/api/bookings/{booking_id}")
    public ResponseBookingDto changeStatusBooking(@PathVariable Long booking_id, @RequestBody NewStatusBooking newStatusBooking) {
        return bookingService.changeStatusBooking(booking_id, newStatusBooking);
    }

    @GetMapping("/api/bookings/{booking_id}")
    public ResponseBookingDto getBooking(@PathVariable Long booking_id) {

        return bookingService.getBooking(booking_id);
    }

}
