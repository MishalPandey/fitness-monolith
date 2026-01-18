package mishal.project.fitness.controller;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.LoginRequest;
import mishal.project.fitness.dto.LoginResponse;
import mishal.project.fitness.dto.RegisterRequest;
import mishal.project.fitness.dto.UsersResponse;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.repository.UsersRepository;
import mishal.project.fitness.security.JwtUtils;
import mishal.project.fitness.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersService userService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

//    private  AuthController(UsersService userService) {
//        this.userService = userService;
//    }
    //use RequiredArgsConstructor from lombok to generate constructor for final fields
    //if we will use AllArgsConstructor then it will generate constructor for all fields including non final fields

    @PostMapping("/register")
    public ResponseEntity<UsersResponse> register(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(userService.register(registerRequest));
        // ResponseEntity is used to represent the whole HTTP response: status code, headers, and body.
        // ab esase kya error aayega? yeh bhi bata sakte
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = null;
        try {
            Users users = usersRepository.findByEmail(loginRequest.getEmail());
            if(users == null) {
               return ResponseEntity.status(401).build();
            }
            if(!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
                return ResponseEntity.status(401).build();
            }

            String token = jwtUtils.generateToken(users.getId(), users.getRole().name());

            return ResponseEntity.ok(new LoginResponse(token, userService.mapToResponse(users)));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }

    }
}
