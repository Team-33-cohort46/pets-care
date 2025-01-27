package ait.cohort46.pet.controller;

import ait.cohort46.pet.dao.PetRepository;
import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;
import ait.cohort46.pet.model.Pet;
import ait.cohort46.pet.service.PetService;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pets")
@RestController
@RequiredArgsConstructor
public class PetController {
    private final UserRepository userRepository;
    private final PetService petService;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @PostMapping()
    public PetResponseDto createPet(@RequestBody PetRequestDto petRequestDto) {
        return petService.createPet(petRequestDto);
    }

    @GetMapping("/me")
    public List<PetResponseDto> getAllPets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        List<PetResponseDto> pets = petRepository.findPetsByUserId(user.getId())
                .stream()
                .map(pet -> modelMapper.map(pet, PetResponseDto.class))
                .toList();
        return pets;
    }

    @DeleteMapping("/me/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }
}
