package ait.cohort46.pet.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class PetDto {
    private Long pets_id;
    private Long user_id;
    private String name;
    private String photo;
    private Set<String> type;
}
