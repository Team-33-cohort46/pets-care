package ait.cohort46.user.dao;

import ait.cohort46.petscare.model.Service;
import ait.cohort46.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    long count();

    Page<User> findAll(Pageable pageable);
}
