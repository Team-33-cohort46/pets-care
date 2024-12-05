package ait.cohort46.petscare.dto;

import ait.cohort46.user.dto.UserRequestDto;
import ait.cohort46.user.dto.UserResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseServiceDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private UserResponseDto user;
}
