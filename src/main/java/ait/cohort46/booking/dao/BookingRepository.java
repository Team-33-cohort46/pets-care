package ait.cohort46.booking.dao;

import ait.cohort46.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.pet.id = :petId")
    Booking findBookingByPetId(@Param("petId") Long pet_id);
}
