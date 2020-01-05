package de.htwberlin.de.TheBayOfPirates.registration;

import de.htwberlin.de.TheBayOfPirates.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class FragmentController {

    @CrossOrigin
    @GetMapping(value = "/navbar")
    public ModelAndView getNavbar(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fragments/navbar"); // resources/template/home.html
        modelAndView.addObject("user", new User());
        modelAndView.addObject("principal", principal);
        return modelAndView;
    }
}
