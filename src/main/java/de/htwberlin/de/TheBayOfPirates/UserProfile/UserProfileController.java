package de.htwberlin.de.TheBayOfPirates.UserProfile;


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

    public UserProfileController(UserService userService){
        this.userService = userService;
    }



    @GetMapping(value = "/user/profile={username}")
    public ModelAndView getPostUserProfile(@PathVariable("username") String username, Principal principal) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findByUserName(username);
        modelAndView.addObject("user", new User());
        modelAndView.addObject("useremail", user.get().getEmail());
        modelAndView.addObject("principal", principal);
        modelAndView.addObject("description", "");
        modelAndView.addObject("gender", "");
        modelAndView.setViewName("userProfile");
        return modelAndView;
    }

    @PostMapping(value = "/user/profile")
    public ModelAndView postUserProfile(@RequestParam("description") String description,  @RequestParam("file") MultipartFile file,
                                        @RequestParam("gender") String gender, Principal principal){

        ModelAndView modelAndView = new ModelAndView();
        try {
            String imageName = file.getOriginalFilename();
            User savedPicture = userService.saveUserProfile(file.getBytes(), description, gender, imageName, principal.getName());
            modelAndView.addObject("successMessage", "Upload succeeded!");
            modelAndView.setViewName("redirect:/");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "failed!");
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
