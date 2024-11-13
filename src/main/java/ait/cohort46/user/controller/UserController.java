package ait.cohort46.user.controller;

import ait.cohort46.user.dto.UserEditDto;
import ait.cohort46.user.dto.UserRequestDto;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @DeleteMapping("/auth/me/{user_id}")
    public Boolean deleteUser(@PathVariable Long user_id) {
        return userService.deleteUser(user_id);
    }

    @PutMapping("/auth/me/{user_id}")
    public UserResponseDto updateUser(@PathVariable Long user_id, @RequestBody UserEditDto userEditDto) {
        return userService.updateUser(user_id, userEditDto);
    }
}
