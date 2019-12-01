package de.htwberlin.de.TheBayOfPirates;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username \"" + username + "\" not found!"));
        return user.map(MyUserDetails::new).get();
    }
}
