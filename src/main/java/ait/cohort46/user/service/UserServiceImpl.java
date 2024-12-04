package ait.cohort46.user.service;

import ait.cohort46.review.dao.ReviewRepository;
import ait.cohort46.review.dto.ReviewDto;
import ait.cohort46.review.model.Review;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.*;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserExistsException();
        }
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        User user = User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(password)
                .photo(userRequestDto.getPhoto())
                .description(userRequestDto.getDescription())
                .isDeleted(false)
                .build();
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserExistsException::new);
        if (user.getIsDeleted()) {
            throw new RuntimeException("User is deleted");
        }
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<ReviewDto> getReviews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        List<ReviewDto> reviews = user.getReviews()
                .stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .toList();
        return reviews;
    }

    @Override
    @Transactional
    public Boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserExistsException::new);
        user.setDeleted(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserResponseDto updateUser(UserEditDto userEditDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        if (userEditDto.getFirstName() != null) {
            user.setFirstName(userEditDto.getFirstName());
        }
        if (userEditDto.getLastName() != null) {
            user.setLastName(userEditDto.getLastName());
        }
        user.setDescription(userEditDto.getDescription());
        user.setPhoto(userEditDto.getPhoto());
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public Boolean changePassword(Long user_id, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public void restoreUser(UserRestoreDto userRestoreDto) {
        User user = userRepository.findByEmail(userRestoreDto.getEmail()).orElseThrow(UserExistsException::new);
        if (!user.getIsDeleted()) {
            throw new RuntimeException("User is not deleted");
        }
        user.setPassword(passwordEncoder.encode(userRestoreDto.getPassword()));
        user.setDeleted(false);
        userRepository.save(user);
    }

    @Override
    public UserResponseDto addReview(String email, ReviewDto reviewDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String reviewerEmail = authentication.getName();
        User recipient = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(UserExistsException::new);
        if (reviewerEmail.equals(email)) {
            throw new RuntimeException("You cannot add review for urself");
        }
        Review review = Review.builder()
                .reviewerEmail(reviewerEmail)
                .message(reviewDto.getMessage())
                .stars(reviewDto.getStars())
                .build();
        recipient.addReview(review);
        reviewRepository.save(review);
        return modelMapper.map(userRepository.save(recipient), UserResponseDto.class);
    }


}
