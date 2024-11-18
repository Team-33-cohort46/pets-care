package ait.cohort46.booking.dto;

import ait.cohort46.petscare.model.Service;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailsBookingDto {
    private Service service;
    private LocalDate startDate;
    private LocalDate endDate;
}
