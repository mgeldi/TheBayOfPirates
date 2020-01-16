package de.htwberlin.de.TheBayOfPirates.user;

import de.htwberlin.de.TheBayOfPirates.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.fail;


class UserProfileServiceImplTest {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private File ImageFile;
    private User user;
    private String userMail = "muhammed@gmail.com";
    private final String description = "Blalalalalal";
    private final String Imagename = "Kim-Dotcom-1024x576-c9ef0fee69a91e66.jpg";
    private final String gender = "FGjghbjk";

    private byte[] ImageAsByte;




    @BeforeEach
    void setUpMocks() throws IOException {
        ImageAsByte = gender.getBytes();
        userRepository = Mockito.mock(UserRepository.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        userService = new UserServiceImpl(bCryptPasswordEncoder, userRepository, roleRepository);
        user = Mockito.mock(User.class);
        user = Mockito.mock(User.class);
        ClassLoader classLoader = getClass().getClassLoader();
        ImageFile = new File(classLoader.getResource(Imagename).getFile());
        ImageAsByte = Files.readAllBytes(ImageFile.toPath());
        Mockito.when(userRepository.findByEmail("muhammed@gmail.com")).thenReturn(Optional.of(user));

    }




    @Test
    void saveUserProfile() {
        try {
            userService.saveUserProfile(ImageAsByte, description, gender, Imagename, userMail);
            Mockito.verify(userRepository, Mockito.times(1))
                    .findByEmail("muhammed@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
