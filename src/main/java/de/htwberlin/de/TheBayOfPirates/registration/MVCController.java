package de.htwberlin.de.TheBayOfPirates.registration;

import javax.validation.Valid;

import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MVCController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration"); // resources/templates/register.html
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // resources/template/home.html
        modelAndView.addObject("user", new User());
        modelAndView.addObject("principal", principal);
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin"); // resources/template/admin.html
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
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        else if(userService.userExists(user)){
            modelAndView.addObject("successMessage", "user already exists!");
            System.out.println("User exists!");
        }
        // we will save the user if there are no binding errors
        else {
            if(user == null) throw new Exception("User is null!");
            user.setEnabled(true);
            //todo: add role user when registering
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










