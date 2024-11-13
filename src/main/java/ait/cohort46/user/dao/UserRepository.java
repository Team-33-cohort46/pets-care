package ait.cohort46.user.dao;

import ait.cohort46.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean findByEmail(String email);
}
