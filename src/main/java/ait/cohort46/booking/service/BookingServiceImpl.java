package ait.cohort46.booking.service;

import ait.cohort46.booking.dao.BookingRepository;
import ait.cohort46.booking.dto.CreateBookingDto;
import ait.cohort46.booking.dto.NewStatusBooking;
import ait.cohort46.booking.dto.ResponseBookingDto;
import ait.cohort46.booking.dto.ResponseStatusBookingDto;
import ait.cohort46.booking.dto.exception.BookingInvalidStatusException;
import ait.cohort46.booking.dto.exception.BookingNotFoundException;
import ait.cohort46.booking.model.Booking;
import ait.cohort46.pet.dao.PetRepository;
import ait.cohort46.pet.model.Pet;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public ResponseBookingDto addBooking(CreateBookingDto createBookingDto) {
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(createBookingDto.getServiceId())
                .orElseThrow(ServiceNotFoundException::new);
        Pet pet = petRepository.findById(createBookingDto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        Booking booking = Booking.builder()
                .service(service)
                .pet(pet)
                .startDate(createBookingDto.getStartDate())
                .endDate(createBookingDto.getEndDate())
                .status("pending")
                .build();

        booking = bookingRepository.save(booking);
        ResponseBookingDto responseBookingDto = modelMapper.map(booking, ResponseBookingDto.class);
        responseBookingDto.setServiceId(service.getId());
        responseBookingDto.setPetId(pet.getId());
        responseBookingDto.setPrice(service.getPrice());
        return responseBookingDto;
    }

    @Override
    public ResponseStatusBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(BookingNotFoundException::new);
        //изменить статус брони можно только если текущий статус pending
        if (booking.getStatus().equals("pending")) {
            // Получаем информацию о текущем аутентифицированном пользователе
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName(); // Получаем имя пользователя из аутентификационных данных
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(UserExistsException::new);

            User owner = booking.getPet().getUser();
            User sitter = booking.getService().getUser();

            //эти статусы может присвоить только ситтер, который указан в сервисе
            if (user.equals(sitter) && (newStatusBooking.getStatus().equals("rejected") || newStatusBooking.getStatus().equals("confirmed"))) {
                booking.setStatus(newStatusBooking.getStatus());
                booking = bookingRepository.save(booking);
            } else { //отменить бронь может только владелец - инициатор брони
                if (user.equals(owner) && newStatusBooking.getStatus().equals("cancelled")) {
                    booking.setStatus(newStatusBooking.getStatus());
                    booking = bookingRepository.save(booking);
                } else {
                    throw new BookingInvalidStatusException();
                }
            }

            return modelMapper.map(booking, ResponseStatusBookingDto.class);
        } else {
            throw new BookingInvalidStatusException();
        }
    }

    @Override
    public ResponseBookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingNotFoundException::new);
        return modelMapper.map(booking, ResponseBookingDto.class);
    }

}
