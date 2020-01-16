package de.htwberlin.de.TheBayOfPirates.UserProfile;


import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    public UserProfileController(UserService userService){
        this.userService = userService;
    }



    @GetMapping(value = "/user/profile")
    public ModelAndView getPostUserProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("description", "");
        modelAndView.addObject("gender", "");
        modelAndView.setViewName("userProfile");
        return modelAndView;
    }

    @PostMapping(value = "/user/profile")
    public ModelAndView postUserProfile(@RequestParam("description") String description,  @RequestParam("file") MultipartFile file,
                                        @RequestParam("gender") String gender, String mail){

        ModelAndView modelAndView = new ModelAndView();
        MultipartFile multipartFile = null;

        try {
            String imageName = multipartFile.getOriginalFilename();
            User savedPicture = userService.saveUserProfile(file.getBytes(), description, gender, imageName, mail);
            modelAndView.addObject("successMessage", "Upload succeeded!");
            modelAndView.setViewName("redirect:/user/profile");
        } catch (Exception e) {
            modelAndView.addObject("error", "failed!");
            modelAndView.setViewName("redirect:/user/profile");
        }
        return modelAndView;
    }
}
