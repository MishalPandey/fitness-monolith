package mishal.project.fitness.security;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email);
        if(users == null ) {
            throw new RuntimeException("User not Found" + email);
        }

        return User.builder()
                .username(users.getEmail())
                .password(users.getPassword())
                .roles(users.getRole().name())
                .build();
    }
}
