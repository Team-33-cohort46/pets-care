package ait.cohort46;

import ait.cohort46.booking.dao.BookingRepository;
import ait.cohort46.pet.dao.PetRepository;
import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;
import ait.cohort46.pet.model.Pet;
import ait.cohort46.pet.service.PetServiceImpl;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.model.User;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ModelMapper modelMapper;  // Мокируем ModelMapper

    @Mock
    private JwtUtils jwtUtils;  // Мокируем JwtUtils (если используется)

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Мокируем контекст безопасности
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void createPet_ShouldThrowException_WhenUserNotFound() {
        // Мокируем Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        // Мокируем UserRepository, чтобы он не нашел пользователя
        when(userRepository.findByEmailAndIsDeletedFalse("test@example.com")).thenReturn(Optional.empty());

        // Мокируем DTO запроса
        PetRequestDto petRequestDto = new PetRequestDto();
        petRequestDto.setName("Buddy");
        petRequestDto.setType("Dog");
        petRequestDto.setPhoto("photo-url");

        // Вызов метода и проверка исключения
        assertThrows(UserExistsException.class, () -> petService.createPet(petRequestDto));

        // Проверка взаимодействия с моками
        verify(userRepository, times(1)).findByEmailAndIsDeletedFalse("test@example.com");
        verify(petRepository, times(0)).save(any(Pet.class));
    }
}
