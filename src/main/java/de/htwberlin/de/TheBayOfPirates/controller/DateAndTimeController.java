package de.htwberlin.de.TheBayOfPirates.controller;

import de.htwberlin.de.TheBayOfPirates.model.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DateAndTimeController {

    @Autowired
    private DateAndTime dateAndTime;


    /**
     * Request Mapping for /about (About Page). We fill the localdatetime with the information from our model.
     * @param model model
     * @return AboutUs the view which is to be returned
     */
    @RequestMapping({"/about"})
    public String dateAndTime(Model model) {
        model.addAttribute("localdatetime", dateAndTime.getDateAndTime());
        return "AboutUs";
    }
}
