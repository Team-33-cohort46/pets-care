package ait.cohort46.user.service;

import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.UserEditDto;
import ait.cohort46.user.dto.UserRequestDto;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail())) {
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
                .build();
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public Boolean deleteUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(UserExistsException::new);
        userRepository.delete(user);
        return true;
    }

    @Override
    public UserResponseDto updateUser(Long user_id, UserEditDto userEditDto) {
        User user = userRepository.findById(user_id).orElseThrow(UserExistsException::new);
        if (userEditDto.getFirstName() != null) {
            user.setFirstName(userEditDto.getFirstName());
        }
        if (userEditDto.getLastName() != null) {
            user.setLastName(userEditDto.getLastName());
        }
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public Boolean changePassword(Long user_id, String oldPassword, String newPassword) {
        return null;
    }
}
