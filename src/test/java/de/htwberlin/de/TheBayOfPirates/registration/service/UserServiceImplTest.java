package de.htwberlin.de.TheBayOfPirates.registration.service;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserService userService;
    private User mockedUser;
    private User mockedUserInvalid;

    @BeforeEach
    void setupUserServiceAndRepo(){
        RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
        Role mockedRole = Mockito.mock(Role.class);
        mockedRole.setRoleName("USER");
        mockedRole.setRoleID(1);
        Mockito.when(mockedRoleRepo.findByRoleName("USER")).thenReturn(mockedRole);
        UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
        BCryptPasswordEncoder mockedEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        this.userService = new UserServiceImpl(mockedEncoder, mockedUserRepo, mockedRoleRepo);
        mockedUser = Mockito.mock(User.class);
        mockedUser.setEmail("muhammed@gmail.com");
        mockedUser.setName("Muhammed");
        mockedUser.setSurname("Geldi");
        mockedUser.setEncryptedPassword("wohohhhh");
        mockedUser.setUserName("Slayer");
        mockedUserInvalid = Mockito.mock(User.class);
        mockedUserInvalid.setEmail("wrongEmail");
        mockedUserInvalid.setUserName("EE");
        mockedUserInvalid.setEncryptedPassword("1");
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

    @Test
    void saveInvalidUserNotSuccessful() {
        try{
            userService.saveUser(mockedUserInvalid);
            fail("No exception was thrown!");
        } catch(Exception e) {
            //everything went fine!
        }
    }

    @Test
    void userExists() {
    }
}