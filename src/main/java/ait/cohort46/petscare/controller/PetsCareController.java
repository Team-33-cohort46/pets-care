package ait.cohort46.petscare.controller;

import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.service.PetsCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PetsCareController {
    private final PetsCareService petsCareService;

    @GetMapping("/services_categories")
    public Iterable<ServiceCategoryDTO> getServiceCategories() {
        return petsCareService.getServiceCategories();
    }

    @PostMapping("/admin/services_categories")
    public ServiceCategoryDTO addServiceCategory(@RequestBody NewServiceCategoryDto newServiceCategoryDto) {
        return petsCareService.addServiceCategory(newServiceCategoryDto);
    }

    @DeleteMapping("/admin/services_categories/{id}")
    public ServiceCategoryDTO deleteServiceCategory(@PathVariable Integer id) {
        return petsCareService.deleteServiceCategory(id);
    }

    @PatchMapping("/admin/services_categories/{id}")
    public ServiceCategoryDTO updateServiceCategory(@PathVariable Integer id, @RequestBody NewServiceCategoryDto newServiceCategoryDto) {
        return petsCareService.updateServiceCategory(id, newServiceCategoryDto);
    }

    @PostMapping("/services")
    public ServiceDTO addNewService(@RequestBody NewServiceDto newServiceDto) {
        return petsCareService.addNewService(newServiceDto);
    }

    @PatchMapping("/services/{id}")
    public ServiceDTO updateService(@PathVariable Long id, @RequestBody UpdateServiceDto updateServiceDto) {
        return petsCareService.updateService(id, updateServiceDto);
    }

    @DeleteMapping("/services/{id}")
    public ServiceDTO deleteService(@PathVariable Long id) {
        return petsCareService.deleteService(id);
    }

    // api/services?sitterId={id}
    @GetMapping("/services/me")
    public Iterable<ResponseServiceDto> getSitterServices() {
        return petsCareService.getSitterServices();
    }

    @GetMapping("/services")
    public Page<ResponseServiceDto> getAllServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) Long categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        if (categoryId != null) {
            // Если передан categoryId, возвращаем отфильтрованные данные
            return petsCareService.getServicesByCategory(categoryId, pageable);
        }
        return petsCareService.getAllServices(pageable);
    }

}
