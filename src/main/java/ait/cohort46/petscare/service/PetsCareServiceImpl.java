package ait.cohort46.petscare.service;

import ait.cohort46.petscare.dao.ServiceCategoryRepository;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.dto.exception.InvalidDataException;
import ait.cohort46.petscare.dto.exception.ServiceCategoryNotFoundException;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import ait.cohort46.petscare.model.ServiceCategory;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsCareServiceImpl implements PetsCareService{
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
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
        ServiceCategory savedServiceCategory = serviceCategoryRepository.save(serviceCategory);
        return modelMapper.map(savedServiceCategory, ServiceCategoryDTO.class);
    }

    @Transactional
    @Override
    public ServiceDTO addNewService(NewServiceDto newServiceDto) {
    //TODO  get user_id
        // Получаем информацию о текущем аутентифицированном пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Получаем имя пользователя из аутентификационных данных
        System.out.println("currentUsername= " + currentUsername);
        // Получаем текущего пользователя из базы данных по имени (или email)
        User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(UserExistsException::new);

        ServiceCategory serviceCategory = serviceCategoryRepository.findById(newServiceDto.getServiceCategory())
                .orElseThrow(ServiceCategoryNotFoundException::new);

        if (newServiceDto.getPrice() < 0 || newServiceDto.getTitle() == null) {
            throw new InvalidDataException();
        }

        ait.cohort46.petscare.model.Service service = ait.cohort46.petscare.model.Service.builder()
                .user(user)
                .serviceCategory(serviceCategory)
                .title(newServiceDto.getTitle())
                .description(newServiceDto.getDescription())
                .price(newServiceDto.getPrice())
                .build();
        ait.cohort46.petscare.model.Service savedService = serviceRepository.save(service);
        return modelMapper.map(savedService, ServiceDTO.class);
    }

    @Transactional
    @Override
    public ServiceDTO updateService(Long id, UpdateServiceDto updateServiceDto) {
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(id)
                .orElseThrow(ServiceNotFoundException::new);
        if (updateServiceDto.getTitle() != null) {
            service.setTitle(updateServiceDto.getTitle());
        }
        if (updateServiceDto.getDescription() != null) {
            service.setDescription(updateServiceDto.getDescription());
        }
        if (updateServiceDto.getPrice() != null) {
            if (updateServiceDto.getPrice() < 0) {
                throw new InvalidDataException();
            }
            service.setPrice(updateServiceDto.getPrice());
        }
        if (updateServiceDto.getPhoto() != null) {
            service.setPhoto(updateServiceDto.getPhoto());
        }
        serviceRepository.save(service);
        return modelMapper.map(service, ServiceDTO.class);
    }

    @Override
    public ServiceDTO deleteService(Long id) {
        ait.cohort46.petscare.model.Service service = serviceRepository.findById(id)
                .orElseThrow(ServiceNotFoundException::new);
        serviceRepository.delete(service);
        return modelMapper.map(service, ServiceDTO.class);
    }

    @Override
    public Iterable<ResponseServiceDto> getSitterServices(Long id) {
        return serviceRepository.findByUserId(id)
                .map(s -> modelMapper.map(s, ResponseServiceDto.class))
                .toList();
    }


}
