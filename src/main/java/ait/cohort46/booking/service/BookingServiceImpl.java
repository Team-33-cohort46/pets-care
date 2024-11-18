package ait.cohort46.booking.service;

import ait.cohort46.booking.dao.BookingRepository;
import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;
import ait.cohort46.booking.dto.exception.BookingNotFoundException;
import ait.cohort46.booking.model.Booking;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
   // private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseBookingDto addBooking(CreateBookingDto createBookingDto) {
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(createBookingDto.getServiceId())
                .orElseThrow(ServiceNotFoundException::new);
        //Pet pet = petRepository.findById(dto.getPetId())
          //      .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        Booking booking = Booking.builder()
                .service(service)
                //.pet(pet)
                .startDate(createBookingDto.getStartDate())
                .endDate(createBookingDto.getEndDate())
                .status("pending")
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, ResponseBookingDto.class);
    }

    @Override
    public ResponseBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(BookingNotFoundException::new);
        if (newStatusBooking.getStatus().equals("cancelled") || newStatusBooking.getStatus().equals("confirmed")) {
            booking.setStatus(newStatusBooking.getStatus());
        }
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, ResponseBookingDto.class);
    }

    @Override
    public ResponseBookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingNotFoundException::new);
        return modelMapper.map(booking, ResponseBookingDto.class);
    }

}
