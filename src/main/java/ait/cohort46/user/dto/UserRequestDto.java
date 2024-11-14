package ait.cohort46.user.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private String description;
}
