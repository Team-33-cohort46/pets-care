package ait.cohort46.petscare.dao;

import ait.cohort46.petscare.dto.ResponseServiceDto;
import ait.cohort46.petscare.model.Service;
import ait.cohort46.user.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s WHERE s.user.id = :userId AND s.isDeleted = false")
    Stream<Service> findByUserId(@Param("userId") Long user_id);

    Page<Service> findAll(Pageable pageable);

    @Query("SELECT s FROM Service s WHERE s.serviceCategory.id = :categoryId AND s.isDeleted = false")
    Page<Service> findByServiceCategoryId(@Param("categoryId") Long category_id, Pageable pageable);
}
