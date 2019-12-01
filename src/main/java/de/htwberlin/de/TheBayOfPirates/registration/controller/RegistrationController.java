package de.htwberlin.de.TheBayOfPirates.registration.controller;

import javax.validation.Valid;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.service.UserService;
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

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login"); // resources/template/login.html
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration"); // resources/templates/register.html
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home"); // resources/template/home.html
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
        }
        else if(userService.userExists(user)){
            modelAndView.addObject("successMessage", "user already exists!");
            System.out.println("User exists!");
        }
        // we will save the user if, no binding errors
        else {
            if(user == null) throw new Exception("User is null1");
            user.setEnabled(true);
            //todo: add role user when registering
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
            System.out.println("User registered!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView loginUser(@Valid User user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}










