package de.htwberlin.de.TheBayOfPirates.UserProfile;


import de.htwberlin.de.TheBayOfPirates.registration.RegistrationController;
import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/user/profile={username}")
    public ModelAndView getPostUserProfile(@PathVariable("username") String username, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findByUserName(username);
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        modelAndView.addObject("useremail", user.get().getEmail());
        modelAndView.addObject("description", "");
        modelAndView.addObject("image", "");
        modelAndView.addObject("gender", "");
        modelAndView.setViewName("userProfile");
        return modelAndView;
    }

    @PostMapping(value = "/user/profile")
    public ModelAndView postUserProfile(@RequestParam("description") String description, @RequestParam("file") MultipartFile multipartFile,
                                        @RequestParam("gender") String gender, Principal principal) {

        ModelAndView modelAndView = new ModelAndView();
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        String imageName = multipartFile.getOriginalFilename();

        try {
            System.out.println(imageName + " " + multipartFile.getName());
            User savedPicture = userService.saveUserProfile(multipartFile.getBytes(), description, gender, imageName, principal.getName());
            modelAndView.addObject("successMessage", "Successfully updated profile!");

            modelAndView.setViewName("redirect:/user/profile=" + savedPicture.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "Updating profile failed! " + e.getMessage());
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
