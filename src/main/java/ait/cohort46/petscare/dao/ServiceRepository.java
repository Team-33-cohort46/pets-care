package ait.cohort46.petscare.dao;

import ait.cohort46.petscare.dto.ResponseServiceDto;
import ait.cohort46.petscare.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Stream<Service> findServicesByUser(Long id);
}
