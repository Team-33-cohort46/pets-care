package ait.cohort46.pet.service;

import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;
import ait.cohort46.pet.model.Pet;

import java.util.List;

public interface PetService {
    PetResponseDto createPet(PetRequestDto petRequestDto);

//    List<Pet> getAllPets(Long user_id);
}
