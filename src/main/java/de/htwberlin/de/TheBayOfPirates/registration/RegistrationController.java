package de.htwberlin.de.TheBayOfPirates.registration;

import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        modelAndView.setViewName("index"); // resources/template/home.html
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        // Check for the validations
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in the form!");
            modelAndView.addObject("bindingResult", bindingResult);
            System.out.println(bindingResult.toString());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        } else if (userService.userExists(user)) {
            modelAndView.addObject("successMessage", "User with that username or email already exists!");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        // we will save the user if there are no binding errors
        else {
            if (user == null) throw new Exception("User is null!");
            user.setEnabled(true);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
    }

    public static void handleSecurity(ModelAndView modelAndView, Principal principal, UserService userService) {
        if (principal != null) {
            modelAndView.addObject("user", userService.findByUserEmail(principal.getName()).get());
        } else {
            modelAndView.addObject("user", new User());
        }

        modelAndView.addObject("principal", principal);
    }
}










