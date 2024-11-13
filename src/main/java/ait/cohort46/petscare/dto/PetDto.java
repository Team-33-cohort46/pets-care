package ait.cohort46.petscare.dto;

import lombok.Getter;

@Getter
public class PetDto {
    private int pets_id;
    private int user_id;
    private String type;
    private String name;
    private String photo;
}
