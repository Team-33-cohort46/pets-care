package ait.cohort46.petscare.dto.controller;

import ait.cohort46.petscare.dto.NewServiceCategoryDto;
import ait.cohort46.petscare.dto.NewServiceDto;
import ait.cohort46.petscare.dto.ServiceCategoryDTO;
import ait.cohort46.petscare.dto.ServiceDTO;
import ait.cohort46.petscare.service.PetsCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PetsCareController {
    private final PetsCareService petsCareService;

    @GetMapping("/api/services_category")
    public Iterable<ServiceCategoryDTO> getServiceCategories() {
        return petsCareService.getServiceCategories();
    }

    @PostMapping("/api/admin/services_category")
    public ServiceCategoryDTO addServiceCategory(@RequestBody NewServiceCategoryDto newServiceCategoryDto) {
        return petsCareService.addServiceCategory(newServiceCategoryDto);
    }

    @PostMapping("/api/services")
    public ServiceDTO addNewService(@RequestBody NewServiceDto newServiceDto) {
        return petsCareService.addNewService(newServiceDto);
    }

    @PatchMapping("/api/services/{service_id}")
    public ServiceDTO updateService(@PathVariable Integer service_id, @RequestBody ServiceDTO serviceDto) {
        return petsCareService.updateService(service_id, serviceDto);
    }

    @DeleteMapping("/api/services/{service_id}")
    public ServiceDTO deleteService(@PathVariable Integer service_id) {
        return petsCareService.deleteService(service_id);
    }
}
