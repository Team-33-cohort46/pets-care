package ait.cohort46.booking.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateBookingDto {
    private Long serviceId;
    private Long petId;
    private LocalDate startDate;
    private LocalDate endDate;
}
