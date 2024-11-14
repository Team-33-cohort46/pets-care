package ait.cohort46.petscare.service;

import ait.cohort46.petscare.dto.NewServiceCategoryDto;
import ait.cohort46.petscare.dto.NewServiceDto;
import ait.cohort46.petscare.dto.ServiceCategoryDTO;
import ait.cohort46.petscare.dto.ServiceDTO;


public interface PetsCareService {
    Iterable<ServiceCategoryDTO> getServiceCategories();

    ServiceCategoryDTO addServiceCategory(NewServiceCategoryDto newServiceCategoryDto);

    ServiceDTO addNewService(NewServiceDto newServiceDto);

    ServiceDTO updateService(Integer id, ServiceDTO serviceDto);

    ServiceDTO deleteService(Integer id);
}
