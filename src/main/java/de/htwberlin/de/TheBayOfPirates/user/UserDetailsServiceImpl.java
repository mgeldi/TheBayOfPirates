package de.htwberlin.de.TheBayOfPirates.user;

import de.htwberlin.de.TheBayOfPirates.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<User> userWithEmail = userService.findByUserEmail(username);
        Optional<User> userWithUsername = userService.findByUserName(username);
        UserBuilder builder = null;
        if (userWithEmail.isPresent()) {
            return getBuilder(userWithEmail.get().getEmail(), userWithEmail.get().getPassword(), userWithEmail.get().getRoles()).build();
        } else if (userWithUsername.isPresent()) {
            return getBuilder(userWithUsername.get().getEmail(), userWithUsername.get().getPassword(), userWithUsername.get().getRoles()).build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private UserBuilder getBuilder(String email, String password, Set<Role> roles) {
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(password);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        builder.authorities(grantedAuthorities);
        return builder;
    }
}
