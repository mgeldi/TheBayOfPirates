package de.htwberlin.de.TheBayOfPirates.registration;

import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.service.UserService;
import de.htwberlin.de.TheBayOfPirates.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationControllerTest {


    private MockMvc mockMvc;

    private RegistrationController registrationController;

    private User mockedUser;

    @BeforeEach
    void setUp(){
        mockedUser = Mockito.mock(User.class);
        UserService userService = Mockito.mock(UserServiceImpl.class);
        registrationController = new RegistrationController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void contextLoads() throws Exception{
        assertThat(registrationController).isNotNull();
    }

    @Test
    void registerUserObject() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful()).andExpect();
    }

    @Test
    void registerValidUser() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "werder")
                .param("surname", "werder")
                .param("username", "werder")
                .param("email", "werder@gmail.com")
                .param("password", "werder"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login?successMessage=User+is+registered+successfully%21"));
    }

    @Test
    void registerInvalidUser() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "we")
                .param("surname", "werder")
                .param("username", "werder")
                .param("email", "werder@gmail.com")
                .param("password", "werder"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("registration"))
                .andExpect(model().attribute("successMessage", "Please correct the errors in form!"));
    }

    @Test
    void registerExistingUser() throws Exception {
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.userExists(Mockito.any(User.class))).thenReturn(true);
        registrationController = new RegistrationController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
        mockMvc.perform(post("/register")
                .param("name", "werder")
                .param("surname", "werder")
                .param("username", "werder")
                .param("email", "werder@gmail.com")
                .param("password", "werder"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("registration"))
                .andExpect(model().attribute("successMessage", "User already exists!"));
    }
}