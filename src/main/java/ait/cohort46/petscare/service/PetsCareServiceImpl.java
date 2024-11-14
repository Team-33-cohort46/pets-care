package ait.cohort46.petscare.service;

import ait.cohort46.petscare.dao.ServiceCategoryRepository;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.NewServiceCategoryDto;
import ait.cohort46.petscare.dto.NewServiceDto;
import ait.cohort46.petscare.dto.ServiceCategoryDTO;
import ait.cohort46.petscare.dto.ServiceDTO;
import ait.cohort46.petscare.dto.exception.InvalidDataException;
import ait.cohort46.petscare.dto.exception.ServiceCategoryNotFoundException;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import ait.cohort46.petscare.model.ServiceCategory;
import ait.cohort46.petscare.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsCareServiceImpl implements PetsCareService{
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Iterable<ServiceCategoryDTO> getServiceCategories() {
        return serviceCategoryRepository.findAll().stream()
                .map(s -> modelMapper.map(s, ServiceCategoryDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public ServiceCategoryDTO addServiceCategory(NewServiceCategoryDto newServiceCategoryDto) {
        ServiceCategory serviceCategory = ServiceCategory.builder()
                .title(newServiceCategoryDto.getTitle())
                .build();
        serviceCategoryRepository.save(serviceCategory);
        return modelMapper.map(serviceCategory, ServiceCategoryDTO.class);
    }

    @Transactional
    @Override
    public ServiceDTO addNewService(NewServiceDto newServiceDto) {
    //TODO  get user_id
        Long userId = 11111L;
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(newServiceDto.getServiceCategoryId())
                .orElseThrow(ServiceCategoryNotFoundException::new);
        if (newServiceDto.getPrice() < 0 || newServiceDto.getTitle() == null) {
            throw new InvalidDataException();
        }

        ait.cohort46.petscare.model.Service service = ait.cohort46.petscare.model.Service.builder()
                .user_id(userId)
                .serviceCategory(serviceCategory)
                .title(newServiceDto.getTitle())
                .description(newServiceDto.getDescription())
                .price(newServiceDto.getPrice())
                .build();
        serviceRepository.save(service);
        return modelMapper.map(service, ServiceDTO.class);
    }

    @Transactional
    @Override
    public ServiceDTO updateService(Integer id, ServiceDTO serviceDto) {
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(id).orElseThrow(ServiceNotFoundException::new);

        return null;
    }

    @Override
    public ServiceDTO deleteService(Integer id) {
        return null;
    }


}
