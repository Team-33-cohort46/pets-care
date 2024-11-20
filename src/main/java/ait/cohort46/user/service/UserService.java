package ait.cohort46.user.service;

import ait.cohort46.user.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    String createUser(UserRequestDto userRequestDto);

    Boolean deleteUser(Long user_id);

    UserResponseDto updateUser(UserEditDto userEditDto);

    Boolean changePassword(Long user_id, String oldPassword, String newPassword);

    UserResponseDto getUserByEmail(String email);







}
