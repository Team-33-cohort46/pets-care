package ait.cohort46.user.service;

import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.*;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import ait.cohort46.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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
        if (user.getIsDeleted()){
            throw new RuntimeException("User is deleted");
        }
        return modelMapper.map(user, UserResponseDto.class);
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
        if (!user.getIsDeleted()){
            throw new RuntimeException("User is not deleted");
        }
        user.setPassword(passwordEncoder.encode(userRestoreDto.getPassword()));
        user.setDeleted(false);
        userRepository.save(user);
    }
}
