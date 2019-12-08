package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsServiceImpl() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUserEmail(username);
        UserBuilder builder = null;
        if (user.isPresent()) {
            builder = org.springframework.security.core.userdetails.User.withUsername(user.get().getEmail());
            builder.password(user.get().getPassword());
            Set grantedAuthorities = new HashSet<>();
            for (Role role : user.get().getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
                System.out.print(role.getRoleName());
            }
            builder.authorities(grantedAuthorities);

        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return builder.build();
    }
}
