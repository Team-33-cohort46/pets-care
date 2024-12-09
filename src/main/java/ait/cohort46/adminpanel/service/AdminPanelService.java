package ait.cohort46.adminpanel.service;

import ait.cohort46.petscare.dto.*;
import ait.cohort46.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminPanelService {

    Page<UserResponseDto> getAllUsers(Pageable pageable);
}
