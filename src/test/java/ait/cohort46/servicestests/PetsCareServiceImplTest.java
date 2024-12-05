package ait.cohort46.servicestests;

import ait.cohort46.petscare.dao.ServiceCategoryRepository;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.model.Service;
import ait.cohort46.petscare.model.ServiceCategory;
import ait.cohort46.petscare.service.PetsCareServiceImpl;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetsCareServiceImplTest {

    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PetsCareServiceImpl petsCareService;

    @Test
    public void testGetServiceCategories() {
        // Данные для теста
        ServiceCategory category1 = new ServiceCategory(1, "Category 1");
        ServiceCategory category2 = new ServiceCategory(2, "Category 2");

        ServiceCategoryDTO categoryDTO1 = new ServiceCategoryDTO(1, "Category 1");
        ServiceCategoryDTO categoryDTO2 = new ServiceCategoryDTO(2, "Category 2");

        List<ServiceCategory> categories = List.of(category1, category2);
        List<ServiceCategoryDTO> categoryDTOs = List.of(categoryDTO1, categoryDTO2);

        // Мокаем репозиторий и mapper
        when(serviceCategoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(category1, ServiceCategoryDTO.class)).thenReturn(categoryDTO1);
        when(modelMapper.map(category2, ServiceCategoryDTO.class)).thenReturn(categoryDTO2);

        // Вызываем метод
        Iterable<ServiceCategoryDTO> result = petsCareService.getServiceCategories();

        // Проверяем результаты
        assertNotNull(result);
        assertEquals(2, ((Collection<?>) result).size());
        assertTrue(((Collection<?>) result).contains(categoryDTO1));
        assertTrue(((Collection<?>) result).contains(categoryDTO2));
    }

    @Test
    public void testAddServiceCategory() {
        // Данные для теста
        NewServiceCategoryDto newCategoryDto = new NewServiceCategoryDto("New Category");
        ServiceCategory serviceCategory = new ServiceCategory(3, "New Category");
        ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO(3, "New Category");

        // Мокаем поведение репозитория и mapper
        when(serviceCategoryRepository.save(any(ServiceCategory.class))).thenReturn(serviceCategory);
        when(modelMapper.map(serviceCategory, ServiceCategoryDTO.class)).thenReturn(serviceCategoryDTO);

        // Вызываем метод
        ServiceCategoryDTO result = petsCareService.addServiceCategory(newCategoryDto);

        // Проверяем результаты
        assertNotNull(result);
        assertEquals("New Category", result.getTitle());
    }

    @Test
    public void testDeleteServiceCategory() {
        // Данные для теста
        ServiceCategory serviceCategory = new ServiceCategory(1, "Category to Delete");
        ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO(1, "Category to Delete");

        // Мокаем поведение репозитория и mapper
        when(serviceCategoryRepository.findById(1)).thenReturn(Optional.of(serviceCategory));
        when(modelMapper.map(serviceCategory, ServiceCategoryDTO.class)).thenReturn(serviceCategoryDTO);

        // Вызываем метод
        ServiceCategoryDTO result = petsCareService.deleteServiceCategory(1);

        // Проверяем результаты
        assertNotNull(result);
        assertEquals("Category to Delete", result.getTitle());
        verify(serviceCategoryRepository, times(1)).delete(serviceCategory);
    }

    @Test
    public void testAddNewService() {
        // Данные для теста
        NewServiceDto newServiceDto = NewServiceDto.builder()
                .serviceCategory(1)
                .title("New Service")
                .description("Description")
                .price(100.0)
                .build();
        User user = User.builder().id(1l).email("test@example.com").build();
        ServiceCategory serviceCategory = ServiceCategory.builder().id(1).title("Category").build();
        ServiceDTO serviceDTO = ServiceDTO.builder().id(1L).title("New Service").description("Description").price(100.0).build();

        // Мокаем зависимости
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(serviceCategoryRepository.findById(1)).thenReturn(Optional.of(serviceCategory));
        when(serviceRepository.save(any(Service.class))).thenReturn(Service.builder().id(1l).title("New Service").build());
        when(modelMapper.map(any(ait.cohort46.petscare.model.Service.class), eq(ServiceDTO.class)))
                .thenReturn(serviceDTO);

        // Мокаем текущего пользователя в контексте
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Вызываем метод
        ServiceDTO result = petsCareService.addNewService(newServiceDto);

        // Проверяем результаты
        assertNotNull(result);
        assertEquals("New Service", result.getTitle());
        assertEquals(100.0, result.getPrice(), 0.01);
    }

    @Test
    public void testUpdateService() {
        // Данные для теста
        UpdateServiceDto updateServiceDto = UpdateServiceDto.builder()
                .title("Updated Service")
                .description("Updated Description")
                .price(150.0)
                .build();
        ait.cohort46.petscare.model.Service service = ait.cohort46.petscare.model.Service.builder()
                .id(1L)
                .title("Old Service")
                .description("Old Description")
                .price(100.0)
                .build();
        ServiceDTO updatedServiceDTO = ServiceDTO.builder()
                .id(1L)
                .title("Updated Service")
                .description("Updated Description")
                .price(150.0)
                .build();

        // Мокаем зависимости
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(modelMapper.map(service, ServiceDTO.class)).thenReturn(updatedServiceDTO);

        // Вызываем метод
        ServiceDTO result = petsCareService.updateService(1L, updateServiceDto);

        // Проверяем результаты
        assertNotNull(result);
        assertEquals("Updated Service", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(150.0, result.getPrice(), 0.01);
    }

    @Test
    public void testDeleteService() {
        // Данные для теста
        ait.cohort46.petscare.model.Service service = ait.cohort46.petscare.model.Service.builder()
                .id(1L)
                .title("Service to Delete")
                .description("Description")
                .price(100.0)
                .build();
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(1L)
                .title("Service to Delete")
                .description("Description")
                .price(100.0)
                .build();

        // Мокаем зависимости
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(modelMapper.map(service, ServiceDTO.class)).thenReturn(serviceDTO);

        // Вызываем метод
        ServiceDTO result = petsCareService.deleteService(1L);

        // Проверяем результаты
        assertNotNull(result);
        assertEquals("Service to Delete", result.getTitle());
        verify(serviceRepository, times(1)).delete(service);
    }
}
