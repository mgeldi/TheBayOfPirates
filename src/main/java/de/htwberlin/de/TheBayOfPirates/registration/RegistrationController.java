package de.htwberlin.de.TheBayOfPirates.registration;

import javax.validation.Valid;

import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController{

    @Autowired
    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration"); // resources/templates/register.html
        return modelAndView;
    }


    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
            System.out.println(bindingResult.toString());
        }
        else if(userService.userExists(user)){
            modelAndView.addObject("successMessage", "User already exists!");
            System.out.println("User exists!");
        }
        // we will save the user if there are no binding errors
        else {
            if(user == null) throw new Exception("User is null!");
            user.setEnabled(true);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
            System.out.println("User registered!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }


}










