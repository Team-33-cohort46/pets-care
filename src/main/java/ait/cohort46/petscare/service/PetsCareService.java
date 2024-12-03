package ait.cohort46.petscare.service;

import ait.cohort46.petscare.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PetsCareService {
    Iterable<ServiceCategoryDTO> getServiceCategories();

    ServiceCategoryDTO addServiceCategory(NewServiceCategoryDto newServiceCategoryDto);

    ServiceCategoryDTO deleteServiceCategory(Integer id);

    ServiceCategoryDTO updateServiceCategory(Integer id, NewServiceCategoryDto newServiceCategoryDto);

    ServiceDTO addNewService(NewServiceDto newServiceDto);

    ServiceDTO updateService(Long id, UpdateServiceDto updateServiceDto);

    ServiceDTO deleteService(Long id);

    Iterable<ResponseServiceDto> getSitterServices();

    Page<ResponseServiceDto> getServicesByCategory(Long categoryId, Pageable pageable);

    Page<ResponseServiceDto> getAllServices(Pageable pageable);
}
