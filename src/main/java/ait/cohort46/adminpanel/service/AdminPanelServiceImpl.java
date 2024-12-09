package ait.cohort46.adminpanel.service;

import ait.cohort46.petscare.dao.ServiceCategoryRepository;
import ait.cohort46.petscare.dao.ServiceRepository;
import ait.cohort46.petscare.dto.*;
import ait.cohort46.petscare.dto.exception.InvalidDataException;
import ait.cohort46.petscare.dto.exception.ServiceCategoryNotFoundException;
import ait.cohort46.petscare.dto.exception.ServiceNotFoundException;
import ait.cohort46.petscare.model.ServiceCategory;
import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class AdminPanelServiceImpl implements AdminPanelService {
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> modelMapper.map(user, UserResponseDto.class));
    }


}
