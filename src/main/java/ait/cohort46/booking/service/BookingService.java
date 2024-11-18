package ait.cohort46.booking.service;

import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;

public interface BookingService {

    ResponseBookingDto addBooking(CreateBookingDto createBookingDto);

    ResponseBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking);

    ResponseBookingDto getBooking(Long bookingId);

}
