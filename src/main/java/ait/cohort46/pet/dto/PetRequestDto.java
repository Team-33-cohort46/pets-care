package ait.cohort46.pet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequestDto {
    private String name;
    private String type;
    private String photo;
}
