package ait.cohort46.petscare.service;

import ait.cohort46.petscare.dto.*;


public interface PetsCareService {
    Iterable<ServiceCategoryDTO> getServiceCategories();

    ServiceCategoryDTO addServiceCategory(NewServiceCategoryDto newServiceCategoryDto);

    ServiceDTO addNewService(NewServiceDto newServiceDto);

    ServiceDTO updateService(Integer id, UpdateServiceDto updateServiceDto);

    ServiceDTO deleteService(Integer id);
}
