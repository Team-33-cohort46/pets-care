package ait.cohort46.pet.service;

import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;

public interface PetService {
    PetResponseDto createPet(PetRequestDto petRequestDto);
}
