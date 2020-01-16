package de.htwberlin.de.TheBayOfPirates.service;



import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.fail;


class UserProfileServiceImplTest {

    private UserService userService;
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


    @Test
    void uploadPic() {
        try {
            userService.uploadPic(Imagename);
            Mockito.verify(userRepository, Mockito.times(1)).findByImageName(Imagename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
