package ait.cohort46.booking.dto;

import ait.cohort46.pet.model.Pet;
import ait.cohort46.petscare.model.Service;
import ait.cohort46.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBookingDto {
    private Long id;
    private String status;
    private Long serviceId;
    private Long petId;
    private LocalDate startDate;
    private LocalDate endDate;
}
