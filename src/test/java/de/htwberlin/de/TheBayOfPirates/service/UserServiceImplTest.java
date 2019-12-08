package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.repository.RoleRepository;
import de.htwberlin.de.TheBayOfPirates.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    private UserService userService;
    private User mockedUser;
    private User mockedUserInvalid;
    private UserRepository mockedUserRepo;

    @BeforeEach
    void setupUserServiceAndRepo(){
        RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
        Role mockedRole = Mockito.mock(Role.class);
        mockedRole.setRoleName("USER");
        mockedRole.setRoleID(1);
        Mockito.when(mockedRoleRepo.findByRole("USER")).thenReturn(java.util.Optional.of(mockedRole));
        UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
        BCryptPasswordEncoder mockedEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        mockedUser = Mockito.mock(User.class);
        mockedUserInvalid = Mockito.mock(User.class);
        Mockito.when(mockedUser.getEmail()).thenReturn("muhammed@gmail.com");
        Mockito.when(mockedUser.getName()).thenReturn("Muhammed");
        Mockito.when(mockedUser.getSurname()).thenReturn("Geldi");
        Mockito.when(mockedUser.getPassword()).thenReturn("wohohhhh");
        Mockito.when(mockedUser.getUsername()).thenReturn("Slayer");
        Mockito.when(mockedUserInvalid.getUsername()).thenReturn("EE");
        Mockito.when(mockedUserInvalid.getEmail()).thenReturn("wrongEmail");
        Mockito.when(mockedUserInvalid.getPassword()).thenReturn("1");
        Mockito.when(mockedUserRepo.findByEmail("muhammed@gmail.com")).thenReturn(java.util.Optional.of(mockedUser));
        Mockito.when(mockedUserRepo.findByUsername("Slayer")).thenReturn(java.util.Optional.of(mockedUser));
        Mockito.when(mockedUserRepo.findByEmail("wrongEmail")).thenReturn(java.util.Optional.ofNullable(null));
        Mockito.when(mockedUserRepo.findByUsername("EE")).thenReturn(java.util.Optional.ofNullable(null));
        this.mockedUserRepo = mockedUserRepo;
        this.userService = new UserServiceImpl(mockedEncoder, mockedUserRepo, mockedRoleRepo);
    }

    @Test
    void saveUserSuccessful() {
        try{
            userService.saveUser(mockedUser);
        } catch(Exception e) {
            fail("Something went wrong!");
            e.printStackTrace();
        }
    }


    /**
     * not Neccesary because @Valid is spring boot specific, handles wrong user for us
    @Test
    void saveInvalidUserNotSuccessful() {
        try{
            userService.saveUser(mockedUserInvalid);
            fail("No exception was thrown!");
        } catch(Exception e) {
            //everything went fine!
        }
    }
     **/

    @Test
    void userExists() {
        assertTrue(userService.userExists(mockedUser));
        assertFalse(userService.userExists(mockedUserInvalid));
    }

    @Test
    void testFindByUserEmail(){
        userService.findByUserEmail("test");
        verify(mockedUserRepo, times(1)).findByEmail("test");
    }
}