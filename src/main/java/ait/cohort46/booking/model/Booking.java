package ait.cohort46.booking.model;

import ait.cohort46.pet.model.Pet;
import ait.cohort46.petscare.model.Service;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Pet pet;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Setter
    private String status = "pending"; // Возможные значения: pending, confirmed, canceled
}
