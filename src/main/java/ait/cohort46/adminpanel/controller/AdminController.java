package ait.cohort46.adminpanel.controller;

import ait.cohort46.adminpanel.service.AdminPanelService;
import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.service.PetsCareService;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final PetsCareService petsCareService;
    private final AdminPanelService adminPanelService;

//    @GetMapping("/services_categories")
//    public Iterable<ServiceCategoryDTO> getServiceCategories() {
//        return petsCareService.getServiceCategories();
//    }
//
//    @PostMapping("/admin/services_categories")
//    public ServiceCategoryDTO addServiceCategory(@RequestBody NewServiceCategoryDto newServiceCategoryDto) {
//        return petsCareService.addServiceCategory(newServiceCategoryDto);
//    }
//
//    @DeleteMapping("/admin/services_categories/{id}")
//    public ServiceCategoryDTO deleteServiceCategory(@PathVariable Integer id) {
//        return petsCareService.deleteServiceCategory(id);
//    }
//
//    @PatchMapping("/admin/services_categories/{id}")
//    public ServiceCategoryDTO updateServiceCategory(@PathVariable Integer id, @RequestBody NewServiceCategoryDto newServiceCategoryDto) {
//        return petsCareService.updateServiceCategory(id, newServiceCategoryDto);
//    }
//
//    @PostMapping("/services")
//    public ServiceDTO addNewService(@RequestBody NewServiceDto newServiceDto) {
//        return petsCareService.addNewService(newServiceDto);
//    }
//
//    @PatchMapping("/services/{id}")
//    public ServiceDTO updateService(@PathVariable Long id, @RequestBody UpdateServiceDto updateServiceDto) {
//        return petsCareService.updateService(id, updateServiceDto);
//    }
//
//    @DeleteMapping("/services/{id}")
//    public ServiceDTO deleteService(@PathVariable Long id) {
//        return petsCareService.deleteService(id);
//    }
//
//    // api/services?sitterId={id}
//    @GetMapping("/services/me")
//    public Iterable<ResponseServiceDto> getSitterServices() {
//        return petsCareService.getSitterServices();
//    }

    @GetMapping("/admin/users")
    public Page<UserResponseDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
//        if (categoryId != null) {
//            // Если передан categoryId, возвращаем отфильтрованные данные
//            return petsCareService.getServicesByCategory(categoryId, pageable);
//        }
        return adminPanelService.getAllUsers(pageable);
    }

}
