package ait.cohort46.pet.dao;

import ait.cohort46.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(Long userId);

    @Query("SELECT p FROM Pet p WHERE p.user.id = :userId")
    List<Pet> findPetsByUserId(@Param("userId") Long user_id);
}
