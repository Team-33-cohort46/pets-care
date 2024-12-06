package ait.cohort46.booking.service;

import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;
import ait.cohort46.booking.dto.ResponseStatusBookingDto;
import ait.cohort46.petscare.dto.ResponseServiceDto;

public interface BookingService {

    ResponseBookingDto addBooking(CreateBookingDto createBookingDto);

    ResponseStatusBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking);

    ResponseBookingDto getBooking(Long bookingId);

    Iterable<ResponseBookingDto> getBookingsAsOwner();

    Iterable<ResponseBookingDto> getBookingsAsSitter();

}
