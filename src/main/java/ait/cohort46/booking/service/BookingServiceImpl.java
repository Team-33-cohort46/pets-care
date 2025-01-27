package ait.cohort46.booking.service;

import ait.cohort46.booking.dao.BookingRepository;
import ait.cohort46.booking.dto.*;
import ait.cohort46.booking.dto.exception.BookingInvalidStatusException;
import ait.cohort46.booking.dto.exception.BookingNotFoundException;
import ait.cohort46.booking.model.Booking;
import ait.cohort46.pet.dao.PetRepository;
import ait.cohort46.pet.model.Pet;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        //проверить, что дата окончания больше даты начала
        LocalDate dateStart = createBookingDto.getStartDate();
        LocalDate dateEnd = createBookingDto.getEndDate();
        long daysBetween = ChronoUnit.DAYS.between(dateStart, dateEnd) + 1;
        if (daysBetween < 1) {
            throw new BookingInvalidStatusException();
        }

        //возвращать стоимость бронирования как прайс*дни, добавить стоимость бронирования в модель
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
                .price(service.getPrice() * daysBetween)
                .build();

        booking = bookingRepository.save(booking);
        ResponseBookingDto responseBookingDto = modelMapper.map(booking, ResponseBookingDto.class);
        responseBookingDto.setServiceId(service.getId());
        responseBookingDto.setPetId(pet.getId());
        responseBookingDto.setOwnerId(service.getUser().getId());
        responseBookingDto.setSitterId(pet.getUser().getId());
        return responseBookingDto;
    }

    @Override
    public ResponseStatusBookingDto changeStatusBooking(Long id, NewStatusBooking newStatusBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(BookingNotFoundException::new);
        //изменить статус брони можно только если текущий статус pending
        //или confirmed, но это пока не точно
        if (booking.getStatus().equals("pending") || booking.getStatus().equals("confirmed")) {
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
    public BookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingNotFoundException::new);
        Pet pet = petRepository.findById(booking.getPet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(booking.getService().getId())
                .orElseThrow(ServiceNotFoundException::new);

        BookingDto response = modelMapper.map(booking, BookingDto.class);
        response.setOwner(modelMapper.map(pet.getUser(), UserResponseDto.class));
        response.setSitter(modelMapper.map(service.getUser(), UserResponseDto.class));
        response.setServiceTitle(booking.getService().getTitle());
        response.setPetName(booking.getPet().getName());
        return response;
    }

    @Override
    public Iterable<BookingDto> getBookingsAsOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(UserExistsException::new);

        // Находим брони, где пользователь является владельцем питомца
        List<Booking> bookings = bookingRepository.findAllByOwner(user.getId());
        return bookings.stream()
                .map(booking -> {
                    BookingDto response = modelMapper.map(booking, BookingDto.class);
                    response.setServiceTitle(booking.getService().getTitle());
                    response.setPetName(booking.getPet().getName());
                    response.setOwner(modelMapper.map(user, UserResponseDto.class));
                    response.setSitter(modelMapper.map(booking.getService().getUser(), UserResponseDto.class));
                    return response;
                })
                .toList();
    }

    @Override
    public Iterable<BookingDto> getBookingsAsSitter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(UserExistsException::new);

        // Находим брони, где пользователь является ситтером
        List<Booking> bookings = bookingRepository.findAllBySitter(user.getId());
        return bookings.stream()
                .map(booking -> {
                    BookingDto response = modelMapper.map(booking, BookingDto.class);
                    response.setServiceTitle(booking.getService().getTitle());
                    response.setPetName(booking.getPet().getName());
                    response.setSitter(modelMapper.map(user, UserResponseDto.class));
                    response.setOwner(modelMapper.map(booking.getPet().getUser(), UserResponseDto.class));
                    return response;
                })
                .toList();
    }
}
