package ait.cohort46.pet.controller;

import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;
import ait.cohort46.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/pet/register")
    public PetResponseDto createPet(@RequestBody PetRequestDto petRequestDto) {
        return petService.createPet(petRequestDto);
    }
}
