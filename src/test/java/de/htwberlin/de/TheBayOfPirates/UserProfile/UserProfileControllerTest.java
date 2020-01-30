package de.htwberlin.de.TheBayOfPirates.UserProfile;


import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


class UserProfileControllerTest {

    private UserService userService;
    private UserProfileController userProfileController;
    private User mockedUser;
    private MockMvc mockMvc;
    private File imageFile;
    private File falseFile;
    private MockMultipartFile mockedImageFile;
    private byte[] imageAsByte;
    private final String description = "Blalalalalal";
    private final String gender = "FGjghbjk";
    private MockMultipartFile mockedFalseFile;
    private Principal principal;


    @BeforeEach
    void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        imageFile = new File(classLoader.getResource("Kim-Dotcom-1024x576-c9ef0fee69a91e66.jpg").getFile());
        falseFile = new File(classLoader.getResource("Kim-Dotcom-1024x576-c9ef0fee69a91e66.tiff").getFile());
        imageAsByte = Files.readAllBytes(imageFile.toPath());
        userService = Mockito.mock(UserService.class);
        mockedUser = Mockito.mock(User.class);
        principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("werder");
        Mockito.when(mockedUser.getImage()).thenReturn("AA".getBytes());
        userProfileController = new UserProfileController((UserService) userProfileController);
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
        mockedImageFile = new MockMultipartFile(imageFile.getName(), imageFile.getName(),
                "text/plain", Files.readAllBytes(imageFile.toPath()));
        mockedFalseFile = new MockMultipartFile(falseFile.getName(), falseFile.getName(),
                "text/plain", Files.readAllBytes(falseFile.toPath()));
    }

    @Test
    void uploadFalseImageFile() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile").
                file("file", mockedFalseFile.getBytes()).
                param("principal", principal.getName()).
                param("description", description).
                param("gender", gender)).
                andExpect(model().attribute("error", "Updating profile failed! null"));
    }

    @Test
    void postUserProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile").
                file("file", mockedImageFile.getBytes()).
                param("principal", principal.getName()).
                param("description", description).
                param("gender", gender)).
                andExpect(model().attribute("error", "Updating profile failed! null"));
    }


}
