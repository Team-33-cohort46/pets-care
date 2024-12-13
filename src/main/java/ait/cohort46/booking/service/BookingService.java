package ait.cohort46.booking.service;

import ait.cohort46.booking.dto.*;

public interface BookingService {

    ResponseBookingDto addBooking(CreateBookingDto createBookingDto);

    ResponseStatusBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking);

    BookingDto getBooking(Long bookingId);

    Iterable<BookingDto> getBookingsAsOwner();

    Iterable<BookingDto> getBookingsAsSitter();

}
