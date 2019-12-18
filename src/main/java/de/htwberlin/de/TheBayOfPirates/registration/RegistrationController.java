package de.htwberlin.de.TheBayOfPirates.registration;

import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // resources/template/home.html
        modelAndView.addObject("user", new User());
        modelAndView.addObject("principal", principal);
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
            System.out.println(bindingResult.toString());
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        } else if (userService.userExists(user)) {
            modelAndView.addObject("successMessage", "User already exists!");
            System.out.println("User exists!");
        }
        // we will save the user if there are no binding errors
        else {
            if (user == null) throw new Exception("User is null!");
            user.setEnabled(true);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
            System.out.println("User registered!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


}










