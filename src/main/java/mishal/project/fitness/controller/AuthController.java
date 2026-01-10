package mishal.project.fitness.controller;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.RegisterRequest;
import mishal.project.fitness.dto.UsersResponse;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersService userService;

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
}
