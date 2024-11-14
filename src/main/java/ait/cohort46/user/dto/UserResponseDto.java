package ait.cohort46.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private Set<String> roles;
}
