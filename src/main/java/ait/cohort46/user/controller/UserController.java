package ait.cohort46.user.controller;

import ait.cohort46.review.dto.ReviewDto;
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
import java.util.List;

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

    @GetMapping("/me")
    public UserResponseDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @DeleteMapping("/me/{email}")
    public Boolean deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    @PostMapping("/register/restore")
        public void restoreUser(@RequestBody UserRestoreDto userRestoreDto) {
        userService.restoreUser(userRestoreDto);
    }

    @PutMapping("/me")
    public UserResponseDto updateUser(@RequestBody UserEditDto userEditDto) {
        return userService.updateUser(userEditDto);
    }

    @PatchMapping("/user/{email}")
    public UserResponseDto addReview(@PathVariable String email, @RequestBody ReviewDto reviewDto) {
        return userService.addReview(email, reviewDto);
    }

    @GetMapping("/me/reviews")
    public List<ReviewDto> getReviews(){
        return userService.getReviews();
    }

//    @PostMapping("/pet/register/{user_id}")
//    public PetResponseDto createPet(@PathVariable Long user_id, @RequestBody PetRequestDto petRequestDto) {
//        return userService.createPet(user_id, petRequestDto);
//    }
}
