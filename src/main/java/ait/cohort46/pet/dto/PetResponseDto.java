package ait.cohort46.pet.dto;

import ait.cohort46.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetResponseDto {
    private Long id;
    private String name;
    private String type;
    private String photo;
    private User user;
}
