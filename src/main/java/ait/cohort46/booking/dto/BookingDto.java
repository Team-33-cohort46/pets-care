package ait.cohort46.booking.dto;

import ait.cohort46.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String status;
    private String serviceTitle;
    private String petName;
    private UserResponseDto sitter;
    private UserResponseDto owner;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
}
