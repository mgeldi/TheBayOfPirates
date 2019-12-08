package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import org.assertj.core.api.OptionalIntAssert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsServiceImplTest {

    private UserDetailsService detailsService;
    private UserService mockedUserService;
    private User mockedUser;

    @BeforeEach
    void setup() {
        mockedUserService = Mockito.mock(UserService.class);
        mockedUser = Mockito.mock(User.class);
        Mockito.when(mockedUser.getPassword()).thenReturn("123456");
        Mockito.when(mockedUser.getEmail()).thenReturn("test123@gmail.com");
        HashSet<Role> roles = new HashSet<Role>();
        Role mockedRole = Mockito.mock(Role.class);
        Mockito.when(mockedRole.getRoleName()).thenReturn("USER");
        roles.add(mockedRole);
        Mockito.when(mockedUser.getRoles()).thenReturn(roles);
        Optional<User> optUser = Optional.of(mockedUser);
        Mockito.when(mockedUserService.findByUserEmail("test123@gmail.com")).thenReturn(optUser);
        Mockito.when(mockedUserService.findByUserEmail("blubb@gmail.com")).thenReturn(Optional.empty());
        detailsService = new UserDetailsServiceImpl(mockedUserService);
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = null;
        try {
            userDetails = detailsService.loadUserByUsername("test123@gmail.com");
            Mockito.verify(mockedUserService, Mockito.times(1)).findByUserEmail("test123@gmail.com");
            Mockito.verify(mockedUser, Mockito.times(1)).getEmail();
            Mockito.verify(mockedUser, Mockito.times(1)).getPassword();
            Mockito.verify(mockedUser, Mockito.times(1)).getRoles();

            assertEquals("123456", userDetails.getPassword());
            assertEquals("test123@gmail.com", userDetails.getUsername());
            assertEquals("USER", userDetails.getAuthorities().iterator().next().toString());
        } catch (UsernameNotFoundException e) {
            fail("Username should be existent");
            e.printStackTrace();
        }

    }

    @Test
    void loadNonExistentUserByUsername() {
        assertThrows(UsernameNotFoundException.class, () ->{
                    UserDetails userDetails = detailsService.loadUserByUsername("blubb@gmail.com");
                });
        Mockito.verify(mockedUserService, Mockito.times(1)).findByUserEmail("blubb@gmail.com");
    }
}