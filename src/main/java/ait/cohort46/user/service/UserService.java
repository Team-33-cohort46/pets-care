package ait.cohort46.user.service;

import ait.cohort46.user.dto.UserEditDto;
import ait.cohort46.user.dto.UserRequestDto;
import ait.cohort46.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    Boolean deleteUser(Long user_id);

    UserResponseDto updateUser(Long user_id, UserEditDto userEditDto);

    Boolean changePassword(Long user_id, String oldPassword, String newPassword);

    UserResponseDto getUserByEmail(String email);





}
