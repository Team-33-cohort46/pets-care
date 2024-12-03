package ait.cohort46.user.service;

import ait.cohort46.review.dto.ReviewDto;
import ait.cohort46.user.dto.*;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    Boolean deleteUser(String email);

    UserResponseDto updateUser(UserEditDto userEditDto);

    Boolean changePassword(Long user_id, String oldPassword, String newPassword);

    UserResponseDto addReview(String email, ReviewDto reviewDto);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto getCurrentUser();

    List<ReviewDto> getReviews();
}
