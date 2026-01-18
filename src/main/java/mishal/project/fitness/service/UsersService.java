package mishal.project.fitness.service;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.LoginRequest;
import mishal.project.fitness.dto.RegisterRequest;
import mishal.project.fitness.dto.UsersResponse;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.model.UsersRole;
import mishal.project.fitness.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
//Without Using Builder
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersResponse register(RegisterRequest registerRequest) {
        UsersRole role = registerRequest.getRole() != null ? registerRequest.getRole()
                : UsersRole.USERS;
        Users user = new Users();
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);
        // NEVER use @AllArgsConstructor on JPA entities, I was adding this in Users Entity class and
        // assigning values to fields like--- Users user = new Users( registerRequest.getEmail(),
        // registerRequest.getPassword(),...was giving error because Hibernate/JPA was
        // using this constructor that accidentally set the id, making Hibernate/JPA think the entity already existed.
        //What Hibernate actually checks
        //Hibernate decides INSERT vs UPDATE using one thing only:
            //Is the entity ID null or not?
                //ID value	Hibernate behavior
                    //null	--------INSERT
                    //NOT null------UPDATE / MERGE

        Users savedUser = usersRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UsersResponse mapToResponse(Users savedUser) {
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setId(savedUser.getId());
        usersResponse.setEmail(savedUser.getEmail());
        usersResponse.setFirstName(savedUser.getFirstName());
        usersResponse.setLastName(savedUser.getLastName());
        usersResponse.setCreatedAt(savedUser.getCreatedAt());
        usersResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return usersResponse;
    }

    public Users authenticate(LoginRequest loginRequest) {
        Users users = usersRepository.findByEmail(loginRequest.getEmail());
        if (users == null) {
           throw new RuntimeException("Invalid Credentials");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }
        return users;
    }
}
