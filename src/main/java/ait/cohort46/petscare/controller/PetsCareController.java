package ait.cohort46.petscare.controller;

import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.service.PetsCareService;
import lombok.RequiredArgsConstructor;
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

///api/services?sitterId={id}
//    @GetMapping("/services?sitterId={id}")
//    public Iterable<ResponseServiceDto> getSitterServices(@RequestParam Long id) {
//        return petsCareService.getSitterServices(id);
//    }
}
