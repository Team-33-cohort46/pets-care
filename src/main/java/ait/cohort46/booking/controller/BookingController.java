package ait.cohort46.booking.controller;

import ait.cohort46.booking.dto.*;
import ait.cohort46.booking.service.BookingService;
import ait.cohort46.petscare.dto.ResponseServiceDto;
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
    public BookingDto getBooking(@PathVariable Long id) {

        return bookingService.getBooking(id);
    }

    @GetMapping("/bookings/as-owner")
    public Iterable<BookingDto> getBookingsAsOwner() {
        return bookingService.getBookingsAsOwner();
    }

    @GetMapping("/bookings/as-sitter")
    public Iterable<BookingDto> getBookingsAsSitter() {
        return bookingService.getBookingsAsSitter();
    }
}
