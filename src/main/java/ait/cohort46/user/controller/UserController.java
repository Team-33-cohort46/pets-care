package ait.cohort46.user.controller;

import ait.cohort46.user.dto.*;
import ait.cohort46.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PostMapping("/auth/login")
    public UserResponseDto login(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @DeleteMapping("/auth/me/{user_id}")
    public Boolean deleteUser(@PathVariable Long user_id) {
        return userService.deleteUser(user_id);
    }

    @PutMapping("/auth/me")
    public UserResponseDto updateUser(@RequestBody UserEditDto userEditDto) {
        return userService.updateUser(userEditDto);
    }

//    @PostMapping("/pet/register/{user_id}")
//    public PetResponseDto createPet(@PathVariable Long user_id, @RequestBody PetRequestDto petRequestDto) {
//        return userService.createPet(user_id, petRequestDto);
//    }
}
