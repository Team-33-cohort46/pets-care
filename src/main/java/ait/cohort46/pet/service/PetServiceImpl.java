package ait.cohort46.pet.service;

import ait.cohort46.pet.dao.PetRepository;
import ait.cohort46.pet.dto.PetRequestDto;
import ait.cohort46.pet.dto.PetResponseDto;
import ait.cohort46.pet.model.Pet;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ait.cohort46.user.dto.exception.UserExistsException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
private final UserRepository userRepository;
private final PetRepository petRepository;
private final ModelMapper modelMapper;
    @Override
    public PetResponseDto createPet(PetRequestDto petRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        Pet pet = Pet.builder()
                .name(petRequestDto.getName())
                .type(petRequestDto.getType())
                .photo(petRequestDto.getPhoto())
                .user(user)
                .build();
        petRepository.save(pet);
        return modelMapper.map(pet, PetResponseDto.class);
    }

//    @Override
//    public List<Pet> getAllPets(Long user_id) {
//      List<Pet> pets = petRepository.findPetsByUserId(user_id);
//        if (pets.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return pets;
//    }
}
