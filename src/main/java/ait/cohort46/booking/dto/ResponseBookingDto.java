package ait.cohort46.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBookingDto {
    private Long bookingId;
    private String status;
    private DetailsBookingDto details;
}
