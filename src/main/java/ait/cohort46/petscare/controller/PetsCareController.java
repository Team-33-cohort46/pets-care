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
    public ServiceDTO updateService(@PathVariable Long service_id, @RequestBody UpdateServiceDto updateServiceDto) {
        return petsCareService.updateService(service_id, updateServiceDto);
    }

    @DeleteMapping("/api/services/{service_id}")
    public ServiceDTO deleteService(@PathVariable Long service_id) {
        return petsCareService.deleteService(service_id);
    }

    @GetMapping("/api/sitters/{sitterId}/services")
    public Iterable<ResponseServiceDto> getSitterServices(@PathVariable Long sitterId) {
        return petsCareService.getSitterServices(sitterId);
    }
}
