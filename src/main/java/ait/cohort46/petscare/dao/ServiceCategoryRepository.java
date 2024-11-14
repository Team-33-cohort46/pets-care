package ait.cohort46.petscare.dao;

import ait.cohort46.petscare.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {
}
