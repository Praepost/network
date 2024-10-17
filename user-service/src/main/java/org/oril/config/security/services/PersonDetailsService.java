package org.oril.config.security.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.oril.config.security.PersonDetails;
import org.oril.entities.user.User;
import org.oril.entities.user.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        Optional<User> person = userRepo.findByEmail(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new PersonDetails(person.get());
    }
}
