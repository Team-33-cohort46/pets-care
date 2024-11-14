package ait.cohort46.petscare.dao;

import ait.cohort46.petscare.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
