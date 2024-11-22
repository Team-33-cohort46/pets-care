package ait.cohort46.user.controller;

import ait.cohort46.user.dao.UserRepository;
import ait.cohort46.user.dto.*;
import ait.cohort46.user.dto.exception.UserExistsException;
import ait.cohort46.user.model.User;
import ait.cohort46.user.service.UserService;
import ait.cohort46.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        User user = userRepository.findByEmailAndIsDeletedFalse(loginRequest.getEmail()).orElseThrow(UserExistsException::new);
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @GetMapping("/user/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/me/{user_id}")
    public Boolean deleteUser(@PathVariable Long user_id) {
        return userService.deleteUser(user_id);
    //users/{id}
    @DeleteMapping("/me/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("register/restore/{email}")
        public void restoreUser(@PathVariable String email) {
        userService.restoreUser(email);
    }

    @PutMapping("/me")
    public UserResponseDto updateUser(@RequestBody UserEditDto userEditDto) {
        return userService.updateUser(userEditDto);
    }

//    @PostMapping("/pet/register/{id}")
//    public PetResponseDto createPet(@PathVariable Long id, @RequestBody PetRequestDto petRequestDto) {
//        return userService.createPet(id, petRequestDto);
//    }
}
