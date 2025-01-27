package ait.cohort46.booking.dto;

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
    private Long sitterId;
    private Long ownerId;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
}