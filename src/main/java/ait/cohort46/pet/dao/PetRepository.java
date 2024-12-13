package ait.cohort46.pet.dao;

import ait.cohort46.pet.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p WHERE p.user.id = :userId AND p.isDeleted = false")
    List<Pet> findPetsByUserId(@Param("userId") Long user_id);

    Page<Pet> findAll(Pageable pageable);
}
