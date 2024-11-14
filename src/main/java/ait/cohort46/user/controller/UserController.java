package ait.cohort46.user.controller;

import ait.cohort46.user.dto.UserEditDto;
import ait.cohort46.user.dto.UserRequestDto;
import ait.cohort46.user.dto.UserResponseDto;
import ait.cohort46.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
// /auth/login

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/auth/register")
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PostMapping("/auth/login")
    public UserResponseDto login(@RequestBody UserRequestDto loginRequest) {
        // Выполняем аутентификацию пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Получаем данные пользователя и возвращаем как DTO
        return userService.getUserByEmail(loginRequest.getEmail());
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
