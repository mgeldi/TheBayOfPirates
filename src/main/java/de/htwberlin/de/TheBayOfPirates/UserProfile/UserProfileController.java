package de.htwberlin.de.TheBayOfPirates.UserProfile;


import de.htwberlin.de.TheBayOfPirates.registration.RegistrationController;
import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.security.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }


    @ResponseBody
    @GetMapping(value = "/profilepic/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto(@PathVariable("username") String username) throws Exception{
        Optional<User> user = userService.findByUserName(username);
        if(user.isPresent()){
            if(user.get().hasProfilePicture()){
                return user.get().getImage();
            } else {
                throw new Exception("User has no profile picture!");
            }
        } else {
            throw new Exception("User not found!");
        }
    }

    @GetMapping(value = "/user/profile={username}")
    public ModelAndView getPostUserProfile(@PathVariable("username") String username, Principal principal) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findByUserName(username);
        //String filepath = userService.loadProfilePicture(user.get().getUsername());

        //System.out.println(filepath);
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        //modelAndView.addObject("filepath", filepath);
        modelAndView.addObject("profile", user.get());
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
        try {
            String imageName = multipartFile.getOriginalFilename();
            System.out.println(imageName);
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
