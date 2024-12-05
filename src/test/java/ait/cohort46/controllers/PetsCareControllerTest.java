package ait.cohort46.controllers;

import ait.cohort46.petscare.controller.PetsCareController;
import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.service.PetsCareService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetsCareControllerTest {

    @Mock
    private PetsCareService petsCareService; // Замокированная зависимость

    @InjectMocks
    private PetsCareController petsCareController; // Тестируемый контроллер

    @Test
    public void testGetServiceCategories() {
        // Настройка моков
        ServiceCategoryDTO category1 = ServiceCategoryDTO.builder().id(1).title("Category 1").build();
        ServiceCategoryDTO category2 = ServiceCategoryDTO.builder().id(2).title("Category 2").build();
        when(petsCareService.getServiceCategories()).thenReturn(List.of(category1, category2));

        // Вызов тестируемого метода
        Iterable<ServiceCategoryDTO> result = petsCareController.getServiceCategories();

        // Проверки
        assertNotNull(result);
        List<ServiceCategoryDTO> resultList = StreamSupport.stream(result.spliterator(), false).toList();
        assertEquals(2, resultList.size());
        assertEquals("Category 1", resultList.get(0).getTitle());
        assertEquals("Category 2", resultList.get(1).getTitle());

        // Проверка, что метод был вызван
        verify(petsCareService, times(1)).getServiceCategories();
    }
    @Test
    public void testAddServiceCategory() {
        NewServiceCategoryDto newCategory = NewServiceCategoryDto.builder().title("New Category").build();
        ServiceCategoryDTO savedCategory = ServiceCategoryDTO.builder().id(1).title("New Category").build();
        when(petsCareService.addServiceCategory(newCategory)).thenReturn(savedCategory);

        ServiceCategoryDTO result = petsCareController.addServiceCategory(newCategory);

        assertNotNull(result);
        assertEquals("New Category", result.getTitle());
        verify(petsCareService, times(1)).addServiceCategory(newCategory);
    }
    @Test
    public void testDeleteServiceCategory() {
        Integer id = 1;
        ServiceCategoryDTO deletedCategory = ServiceCategoryDTO.builder().id(id).title("Category to delete").build();
        when(petsCareService.deleteServiceCategory(id)).thenReturn(deletedCategory);

        ServiceCategoryDTO result = petsCareController.deleteServiceCategory(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(petsCareService, times(1)).deleteServiceCategory(id);
    }
    @Test
    public void testUpdateServiceCategory() {
        int id = 1;
        NewServiceCategoryDto updatedData = NewServiceCategoryDto.builder().title("Updated Category").build();
        ServiceCategoryDTO updatedCategory = ServiceCategoryDTO.builder().id(id).title("Updated Category").build();
        when(petsCareService.updateServiceCategory(id, updatedData)).thenReturn(updatedCategory);

        ServiceCategoryDTO result = petsCareController.updateServiceCategory(id, updatedData);

        assertNotNull(result);
        assertEquals("Updated Category", result.getTitle());
        verify(petsCareService, times(1)).updateServiceCategory(eq(id), eq(updatedData));
    }
    @Test
    public void testAddNewService() {
        NewServiceDto newService = NewServiceDto.builder()
                .title("New Service")
                .price(100.0)
                .serviceCategory(1)
                .description("Description")
                .build();
        ServiceDTO savedService = ServiceDTO.builder().id(1L).title("New Service").price(100.0).build();
        when(petsCareService.addNewService(newService)).thenReturn(savedService);

        ServiceDTO result = petsCareController.addNewService(newService);

        assertNotNull(result);
        assertEquals("New Service", result.getTitle());
        verify(petsCareService, times(1)).addNewService(newService);
    }
    @Test
    public void testUpdateService() {
        long id = 1L;
        UpdateServiceDto updatedData = UpdateServiceDto.builder().title("Updated Service").build();
        ServiceDTO updatedService = ServiceDTO.builder().id(id).title("Updated Service").build();
        when(petsCareService.updateService(id, updatedData)).thenReturn(updatedService);

        ServiceDTO result = petsCareController.updateService(id, updatedData);

        assertNotNull(result);
        assertEquals("Updated Service", result.getTitle());
        verify(petsCareService, times(1)).updateService(eq(id), eq(updatedData));
    }

    @Test
    public void testDeleteService() {
        Long id = 1L;
        ServiceDTO deletedService = ServiceDTO.builder().id(id).title("Service to delete").build();
        when(petsCareService.deleteService(id)).thenReturn(deletedService);

        ServiceDTO result = petsCareController.deleteService(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(petsCareService, times(1)).deleteService(id);
    }

    // Тест для получения услуг текущего ситтера
    @Test
    public void testGetSitterServices() {
        ResponseServiceDto service1 = ResponseServiceDto.builder().id(1L).title("Service 1").build();
        ResponseServiceDto service2 = ResponseServiceDto.builder().id(2L).title("Service 2").build();
        when(petsCareService.getSitterServices()).thenReturn(List.of(service1, service2));

        Iterable<ResponseServiceDto> result = petsCareController.getSitterServices();

        assertNotNull(result);
        List<ResponseServiceDto> resultList = StreamSupport.stream(result.spliterator(), false).toList();
        assertEquals(2, resultList.size());
        assertEquals("Service 1", resultList.get(0).getTitle());
        assertEquals("Service 2", resultList.get(1).getTitle());

        verify(petsCareService, times(1)).getSitterServices();
    }

    // Тест для получения всех услуг с фильтром по категории
    @Test
    public void testGetAllServices() {
        Pageable pageable = PageRequest.of(0, 10);
        ResponseServiceDto service1 = ResponseServiceDto.builder().id(1L).title("Service 1").build();
        ResponseServiceDto service2 = ResponseServiceDto.builder().id(2L).title("Service 2").build();
        Page<ResponseServiceDto> servicesPage = new PageImpl<>(List.of(service1, service2), pageable, 2);

        when(petsCareService.getServicesByCategory(1L, pageable)).thenReturn(servicesPage);

        Page<ResponseServiceDto> result = petsCareController.getAllServices(0, 10, 1L);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(petsCareService, times(1)).getServicesByCategory(eq(1L), eq(pageable));
    }
}
